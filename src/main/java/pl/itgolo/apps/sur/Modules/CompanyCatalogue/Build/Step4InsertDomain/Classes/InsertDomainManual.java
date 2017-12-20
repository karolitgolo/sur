package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step4InsertDomain.Classes;

import org.apache.commons.net.ftp.FTP;
import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Transporters.FtpTransporter;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.HttpUrlConnectionUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step4InsertDomain.Abstracts.InsertDomain;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

/**
 * The type Import sql home.
 */
public class InsertDomainManual extends InsertDomain {


    private FtpTransporter ftpTransporter;
    private String remoteDirSkeleton;
    private String urlDirSkeleton;

    public InsertDomainManual(String ftpHost, Integer ftpPort, String ftpUser, String ftpPassword, String remoteDirSkeleton, String urlDirSkeleton) throws IOException {
        this.remoteDirSkeleton = remoteDirSkeleton;
        this.urlDirSkeleton = urlDirSkeleton;
        this.ftpTransporter = new FtpTransporter(ftpHost, ftpPort, ftpUser, ftpPassword, true, FTP.BINARY_FILE_TYPE);

    }

    @Override
    protected void before() throws StepCompanyCatalogueException {

    }

    @Override
    protected Boolean authorizationPanel() throws StepCompanyCatalogueException {
        // this class insert manual domain
       return true;
    }

    @Override
    protected Boolean insertDomain() throws StepCompanyCatalogueException {
        // this class insert manual domain
        return true;
    }

    @Override
    protected Boolean validateInsertedDomain() throws StepCompanyCatalogueException {
        try {
            String validateCode = UUID.randomUUID().toString().replace("-", "");
            File validateFile =  File.createTempFile("validateFile", ".txt");
            FileUtils.write(validateCode, validateFile);
            this.ftpTransporter.upload(validateFile, this.remoteDirSkeleton + "/validateFile.txt");
            String contentBody = HttpUrlConnectionUtils.getContentBody(new URL(this.urlDirSkeleton + "/validateFile.txt"), 10, 120);
           if (!validateCode.equals(contentBody)){
               throw new StepCompanyCatalogueException("Remote validate code is incorrect");
           }
            Boolean deletedRemoteValidateFile = this.ftpTransporter.getFtpClient().deleteFile(this.remoteDirSkeleton + "/validateFile.txt");
            if (!deletedRemoteValidateFile){
                throw new StepCompanyCatalogueException("Can not delete validate file txt in remote");
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
