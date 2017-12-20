package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step1UnzipSkeleton.Classes;

import org.apache.commons.net.ftp.FTP;
import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Transporters.FtpTransporter;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.HttpUrlConnectionUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.ZipUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step1UnzipSkeleton.Abstracts.UnzipSkeleton;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The type Unzip skeleton ftp.
 */
public class UnzipSkeletonFtp extends UnzipSkeleton {

    private FtpTransporter ftpTransporter;
    private String remoteDirSkeleton;
    private String urlDirSkeleton;
    private File skeletonZipFile;
    private Boolean deleteRemoteDirIfExist;

    /**
     * Instantiates a new Unzip skeleton ftp.
     *
     * @param ftpServer              the ftp server
     * @param ftpPort                the ftp port
     * @param ftpUser                the ftp user
     * @param ftpPassword            the ftp password
     * @param localPassiveMode       the local passive mode
     * @param remoteDirSkeleton      the remote dir skeleton
     * @param urlDirSkeleton         the url dir skeleton
     * @param skeletonZipFile        the skeleton zip file
     * @param deleteRemoteAppIfExist the delete remote app if exist
     * @throws IOException the io exception
     */
    public UnzipSkeletonFtp(String ftpServer, Integer ftpPort, String ftpUser, String ftpPassword, Boolean localPassiveMode, String remoteDirSkeleton, String urlDirSkeleton, File skeletonZipFile, Boolean deleteRemoteAppIfExist) throws IOException {
        this.remoteDirSkeleton = remoteDirSkeleton;
        this.urlDirSkeleton = urlDirSkeleton;
        this.skeletonZipFile = skeletonZipFile;
        this.deleteRemoteDirIfExist = deleteRemoteAppIfExist;
        this.ftpTransporter = new FtpTransporter(ftpServer, ftpPort, ftpUser, ftpPassword, localPassiveMode, FTP.BINARY_FILE_TYPE);
    }

    @Override
    protected void before() throws StepCompanyCatalogueException {
        Boolean indexExist = false;
        try {
            LogService.debug("Launch delete remote directory");
            if (this.deleteRemoteDirIfExist){
                ftpTransporter.delete(remoteDirSkeleton);
            }
            LogService.debug("Finish delete remote directory");
            String urlIndexPhp = String.format("%1$s/index.php", this.urlDirSkeleton);
            URL url = new URL(urlIndexPhp);
            HttpUrlConnectionUtils.canConnect(url, 45, 1);
            indexExist = true;
        } catch (Exception e) {
            // this exception is not requite
            // because only check exist index.php
            // and return to variable indexExist
        }
        if (indexExist) {
            throw new StepCompanyCatalogueException("In remote directory of skeleton exist file 'index.php'");
        }
    }

    @Override
    protected Boolean validateZipFile() throws StepCompanyCatalogueException {
        return ZipUtils.isValid(this.skeletonZipFile);
    }

    @Override
    protected Boolean validateFileTransporter() throws StepCompanyCatalogueException {
        // is not required because is valid
        // in constructor by create new FTP client
        return true;
    }

    @Override
    protected Boolean uploadSkeleton() throws StepCompanyCatalogueException {
        try {
            ftpTransporter.upload(this.skeletonZipFile, this.remoteDirSkeleton + "/" + this.skeletonZipFile.getName());
            return true;
        } catch (IOException e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateUploadedSkeleton() throws StepCompanyCatalogueException {
        try {
            String urlSkeletonZip = String.format("%1$s/%2$s", this.urlDirSkeleton, this.skeletonZipFile.getName());
            URL url = new URL(urlSkeletonZip);
            HttpURLConnection conn = HttpUrlConnectionUtils.canConnect(url, 45, 120);
            Long weightRemote = conn.getContentLengthLong();
            Long weight = this.skeletonZipFile.length();
            if (!weight.equals(weightRemote)) {
                throw new IOException("In validate upload file to FTP, file in server not equal length to file local");
            }
            conn.disconnect();
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean remoteUnzipSkeleton() throws StepCompanyCatalogueException {
        try {
            String unzipTemplate = FileUtils.readFromResource("/pl/itgolo/apps/sur/Modules/CompanyCatalogue/Build/Step1UnzipSkeleton/Resources/unzipTemplate.php");
            unzipTemplate = unzipTemplate.replace("{{fileNameZip}}", this.skeletonZipFile.getName());
            File unzipFile = File.createTempFile("unzip", ".php");
            FileUtils.write(unzipTemplate, unzipFile);
            this.ftpTransporter.upload(unzipFile, this.remoteDirSkeleton + "/unzip.php");
            HttpUrlConnectionUtils.canConnect(new URL(this.urlDirSkeleton + "/unzip.php"), 120, 1);
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateRemoteUnzipSkeleton() throws StepCompanyCatalogueException {
        try {
            HttpUrlConnectionUtils.canConnect(new URL(this.urlDirSkeleton + "/index.php"), 45, 1);
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {
       try {
           Boolean deletedRemoteUnzipFile = this.ftpTransporter.getFtpClient().deleteFile(this.remoteDirSkeleton + "/unzip.php");
           Boolean deletedRemoteSkeletonZip = this.ftpTransporter.getFtpClient().deleteFile(this.remoteDirSkeleton + "/" + this.skeletonZipFile.getName());
            if (!deletedRemoteUnzipFile){
                throw new StepCompanyCatalogueException("Can not delete remote unzip.php file");
            }
           if (!deletedRemoteSkeletonZip){
               throw new StepCompanyCatalogueException("Can not delete remote "+this.skeletonZipFile.getName()+" file");
           }
       } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
       }
    }

}
