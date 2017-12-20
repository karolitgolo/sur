package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step1InsertAsset.Classes;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Transporters.FtpTransporter;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.HttpUrlConnectionUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step1InsertAsset.Abstracts.InsertAsset;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The type Import sql home.
 */
public class InsertAssetWordpress extends InsertAsset {


    private FtpTransporter ftpTransporter;
    private String remoteDirSkeleton;
    private String urlDirSkeleton;

    public InsertAssetWordpress(String ftpHost, Integer ftpPort, String ftpUser, String ftpPassword, String remoteDirSkeleton, String urlDirSkeleton) throws IOException {
        this.remoteDirSkeleton = remoteDirSkeleton;
        this.urlDirSkeleton = urlDirSkeleton;
        this.ftpTransporter = new FtpTransporter(ftpHost, ftpPort, ftpUser, ftpPassword, true, FTP.BINARY_FILE_TYPE);

    }

    @Override
    protected void before() throws StepCompanyCatalogueException {

    }

    @Override
    protected Boolean sendAssets() throws StepCompanyCatalogueException {
        try {
            File imageBgSearchQuery = File.createTempFile("bg-search-query_", ".jpg");
            FileUtils.resourceToFile("/pl/itgolo/apps/sur/Modules/CompanyCatalogue/PreConfig/Step1InsertAsset/Resources/bg-search-query_.jpg", imageBgSearchQuery);
            this.ftpTransporter.upload(imageBgSearchQuery, this.remoteDirSkeleton + "/wp-content/plugins/allemechanik/modules/themes/assets/images/" + getImageBgSearchQueryNameRemote(this.urlDirSkeleton));
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    public static String getImageBgSearchQueryNameRemote(String urlDirSkeleton) throws MalformedURLException, StepCompanyCatalogueException {
        URL urlApp = new URL(urlDirSkeleton);
        String hostApp = urlApp.getHost();
        Integer quantityPlInHost = StringUtils.countMatches(hostApp, ".pl");
        if (!quantityPlInHost.equals(1)) {
            throw new StepCompanyCatalogueException("Host not contains one '.pl' string");
        }
        return String.format("bg-search-query_%1$s.jpg", hostApp.replace(".pl", ""));
    }

    @Override
    protected Boolean validateSentAssets() throws StepCompanyCatalogueException {
        try {
            HttpUrlConnectionUtils.canConnect(new URL(this.urlDirSkeleton + "/wp-content/plugins/allemechanik/modules/themes/assets/images/" + getImageBgSearchQueryNameRemote(this.urlDirSkeleton)), 5, 120);
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean clearCacheAssets() throws StepCompanyCatalogueException {
        try {
            this.ftpTransporter.delete(this.remoteDirSkeleton + "/assets");
            return true;
        } catch (IOException e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateClearCacheAssets() throws StepCompanyCatalogueException {
        Boolean noExistCacheAssets = false;
        try {
            HttpUrlConnectionUtils.canConnect(new URL(this.urlDirSkeleton + "/assets/all.min.css"), 60, 5);
        } catch (Exception e) {
            // this exception is not require service
            // because this only validate remote url
            noExistCacheAssets = true;
        }
        return noExistCacheAssets;
    }


    @Override
    protected void after() throws StepCompanyCatalogueException {

    }
}
