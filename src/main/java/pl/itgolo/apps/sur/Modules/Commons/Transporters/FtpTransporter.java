package pl.itgolo.apps.sur.Modules.Commons.Transporters;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The type Ftp transporter.
 */
public class FtpTransporter {

    /**
     * The Ftp client.
     */
    FTPClient ftpClient;
    /**
     * The Ftp server.
     */
    String ftpServer;
    /**
     * The Ftp port.
     */
    Integer ftpPort;
    /**
     * The Ftp user.
     */
    String ftpUser;
    /**
     * The Ftp password.
     */
    String ftpPassword;
    /**
     * The Local passive mode.
     */
    Boolean localPassiveMode;
    /**
     * The File type.
     */
    Integer fileType;

    /**
     * The Safe delete main remote dir.
     */
    Boolean safeDeleteMainRemoteDir;

    Long countUploadBytes;

    Integer percentageUploadedBytes;

    /**
     * Instantiates a new Ftp transporter.
     *
     * @param ftpServer        the ftp server
     * @param ftpPort          the ftp port
     * @param ftpUser          the ftp user
     * @param ftpPassword      the ftp password
     * @param localPassiveMode the local passive mode
     * @param fileType         the file type
     * @throws IOException the io exception
     */
    public FtpTransporter(String ftpServer, Integer ftpPort, String ftpUser, String ftpPassword, Boolean localPassiveMode, Integer fileType) throws IOException {
        this.ftpServer = ftpServer;
        this.ftpPort = ftpPort;
        this.ftpUser = ftpUser;
        this.ftpPassword = ftpPassword;
        this.localPassiveMode = localPassiveMode;
        this.fileType = fileType;
        this.safeDeleteMainRemoteDir = true;
        initFTPClient();
    }

    /**
     * Close ftp client.
     *
     * @throws IOException the io exception
     */
    public void closeFtpClient() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }


    private void initFTPClient() throws IOException {
        ftpClient = new FTPClient();
        ftpClient.connect(ftpServer, ftpPort);
        if (!ftpClient.login(ftpUser, ftpPassword)) {
            throw new IOException("Can not login to FTP");
        }
        if (localPassiveMode) {
            ftpClient.enterLocalPassiveMode();
        }
        ftpClient.setFileType(fileType);
    }


    /**
     * Make directories.
     *
     * @param file       the file
     * @param remotePath the remote path
     * @throws IOException the io exception
     */
    public void makeDirectories(File file, String remotePath) throws IOException {
        String remoteDir = getCorrectRemoteDir(remotePath, file);
        ftpClient.changeWorkingDirectory("/");
        boolean dirExists = true;
        String[] directories = remoteDir.split("/");
        for (String dir : directories) {
            if (!dir.isEmpty()) {
                if (dirExists) {
                    dirExists = ftpClient.changeWorkingDirectory(dir);
                }
                if (!dirExists) {
                    if (!ftpClient.makeDirectory(dir)) {
                        throw new IOException("Unable to create remote directory '" + dir + "'.  error='" + ftpClient.getReplyString() + "'");
                    }
                    if (!ftpClient.changeWorkingDirectory(dir)) {
                        throw new IOException("Unable to change into newly created remote directory '" + dir + "'.  error='" + ftpClient.getReplyString() + "'");
                    }
                }
            }
        }
    }

    /**
     * Upload.
     *
     * @param file       the file
     * @param remotePath the remote path
     * @throws IOException the io exception
     */
    public void upload(File file, String remotePath) throws IOException {
        makeDirectories(file, remotePath);
        if (file.isFile()) {
            ftpClient.changeWorkingDirectory(getCorrectRemoteDir(remotePath, file));
            InputStream inputStream = new FileInputStream(file);
            String fileName = Paths.get(remotePath).getFileName().toString();
            OutputStream outputStream = this.ftpClient.storeFileStream(fileName);
            if (Objects.isNull(outputStream)) {
                throw new IOException(ftpClient.getReplyString());
            }
            Integer sizeBytes = 4096;
            byte[] bytesIn = new byte[sizeBytes];
            int read = 0;
            this.countUploadBytes = 0L;
            this.percentageUploadedBytes = 0;
            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
                calculateUploadBytes(sizeBytes, file.length(), file, remotePath);
            }
            inputStream.close();
            outputStream.close();
            boolean completed = this.ftpClient.completePendingCommand();
            if (!completed) {
                throw new IOException("Can not upload file to FTP: " + remotePath);
            }
        }
    }

    private void calculateUploadBytes(Integer sizeBytes, Long fileLength, File file, String remotePath) {
        this.countUploadBytes += sizeBytes;
        Long percentage = Math.round(((double)this.countUploadBytes / fileLength)* 100.0);
        if (percentage.intValue() != this.percentageUploadedBytes.intValue() && percentage < 101) {
            this.percentageUploadedBytes = percentage.intValue();
            LogService.debug(String.format("Uploading file FTP %1$s%% '%2$s' from '%3$s'", this.percentageUploadedBytes, remotePath, file.getAbsolutePath()));
        }
    }

    /**
     * Delete.
     *
     * @param remoteDir the remote dir
     * @throws IOException the io exception
     */
    public void delete(String remoteDir) throws IOException {
            if (this.safeDeleteMainRemoteDir){
                if (remoteDir.trim().equals("/") || remoteDir.isEmpty()){
                    throw new IOException("Can not delete main remote directory. Try change 'safeDeleteMainRemoteDir' to false");
                }
            }
            FTPFile[] files=this.ftpClient.listFiles(remoteDir);
            if(files.length>0) {
                for (FTPFile ftpFile : files) {
                    if(ftpFile.isDirectory()){
                        if (!ftpFile.getName().equals(".") && !ftpFile.getName().equals("..")){
                            delete(remoteDir + "/" + ftpFile.getName());
                        }
                    }
                    else {
                       this.ftpClient.deleteFile(remoteDir + "/" + ftpFile.getName());
                    }

                }
            }
            this.ftpClient.removeDirectory(remoteDir);
    }

    private String getCorrectRemoteDir(String remotePath, File file) {
        if (file.isFile()) {
            remotePath = Paths.get(remotePath).getParent().toString();
        }
        remotePath = remotePath.replaceAll("\\\\", "/");
        return remotePath;
    }

    /**
     * Sets safe delete main remote dir.
     *
     * @param safeDeleteMainRemoteDir the safe delete main remote dir
     */
    public void setSafeDeleteMainRemoteDir(Boolean safeDeleteMainRemoteDir) {
        this.safeDeleteMainRemoteDir = safeDeleteMainRemoteDir;
    }

    /**
     * Gets ftp client.
     *
     * @return the ftp client
     */
    public FTPClient getFtpClient() {
        return ftpClient;
    }

    /**
     * Download.
     *
     * @param toFile     the to file
     * @param remoteFile the remote file
     * @throws IOException the io exception
     */
    public void download(File toFile, String remoteFile) throws IOException {
        try {
            String ftpUrl = "ftp://%s:%s@%s%s;type=i";
            ftpUrl = String.format(ftpUrl, URLEncoder.encode(this.ftpUser, "UTF-8"), URLEncoder.encode(this.ftpPassword, "UTF-8"), this.ftpServer, remoteFile);
            URL url = new URL(ftpUrl);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(toFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

        } catch (Throwable t) {
            throw new IOException(t.getMessage(), t);
        }
    }
}