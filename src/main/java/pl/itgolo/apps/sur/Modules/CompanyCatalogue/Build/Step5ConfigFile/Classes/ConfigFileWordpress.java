package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step5ConfigFile.Classes;

import org.apache.commons.net.ftp.FTP;
import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Transporters.FtpTransporter;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.HttpUrlConnectionUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step5ConfigFile.Abstracts.ConfigFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * The type Import sql home.
 */
public class ConfigFileWordpress extends ConfigFile {


    private FtpTransporter ftpTransporter;
    private String remoteDirSkeleton;
    private String urlDirSkeleton;
    private String databaseHost;
    private String databaseName;
    private String databaseUser;
    private String databasePassword;
    private File wpConfigTemplateFile;

    public ConfigFileWordpress(String ftpHost, Integer ftpPort, String ftpUser, String ftpPassword, String remoteDirSkeleton, String urlDirSkeleton, String databaseHost, String databaseName, String databaseUser, String databasePassword, File wpConfigTemplateFile) throws IOException {
        this.remoteDirSkeleton = remoteDirSkeleton;
        this.urlDirSkeleton = urlDirSkeleton;
        this.databaseHost = databaseHost;
        this.databaseName = databaseName;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
        this.wpConfigTemplateFile = wpConfigTemplateFile;
        this.ftpTransporter = new FtpTransporter(ftpHost, ftpPort, ftpUser, ftpPassword, true, FTP.BINARY_FILE_TYPE);

    }

    @Override
    protected void before() throws StepCompanyCatalogueException {

    }

    @Override
    protected Boolean sendFile() throws StepCompanyCatalogueException {
        try {
            String wpConfigTemplate = FileUtils.readFromFile(this.wpConfigTemplateFile);
            wpConfigTemplate = wpConfigTemplate.replace("{{DB_NAME}}", this.databaseName);
            wpConfigTemplate = wpConfigTemplate.replace("{{DB_USER}}", this.databaseUser);
            wpConfigTemplate = wpConfigTemplate.replace("{{DB_PASSWORD}}", this.databasePassword);
            wpConfigTemplate = wpConfigTemplate.replace("{{DB_HOST}}", this.databaseHost);
            File wpConfigFile = File.createTempFile("wpConfigFile", ".php");
            FileUtils.write(wpConfigTemplate, wpConfigFile);
            this.ftpTransporter.upload(wpConfigFile, this.remoteDirSkeleton + "/wp-config.php");
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateSentFile() throws StepCompanyCatalogueException {
        try {
            String contentBody = HttpUrlConnectionUtils.getContentBody(new URL(this.urlDirSkeleton), 20, 120);
            if (!contentBody.contains(this.urlDirSkeleton)) {
                throw new StepCompanyCatalogueException(String.format("After upload config file, domain: %1$s not contains '%2$s'", this.urlDirSkeleton, this.urlDirSkeleton));
            }
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {

    }
}
