package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.SequenceCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.ISequenceCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step1UnzipSkeleton.Classes.UnzipSkeletonFtp;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step2NewDatabase.Classes.NewDatabaseHome;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step3ImportSql.Classes.ImportSqlHome;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step4InsertDomain.Classes.InsertDomainManual;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step5ConfigFile.Classes.ConfigFileWordpress;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step6AddContent.Classes.AddContentWordpress;
import pl.itgolo.libs.chromium.Browser;

import java.io.File;

/**
 * The type Company catalogue build home.
 */
public class CompanyCatalogueBuildHome implements ISequenceCompanyCatalogue {

    private Browser browser;
    private String ftpHost;
    private String ftpPort;
    private final String ftpUser;
    private final String ftpPassword;
    private String remoteDirApp;
    private String urlDirApp;
    private String appSkeletonZipPath;
    private Boolean deleteRemoteAppIfExist;
    private final String homeUrlPanel;
    private final String homeUserPanel;
    private final String homePasswordPanel;
    private final String homeHostingId;
    private final String databaseNameSuffix;
    private final String databasePassword;
    private final String databaseDescription;
    private String databaseHost;
    private String databasePort;
    private String appEmailAdministrator;
    private final String importSqlTemplateFile;
    private final String wpConfigTemplateFile;

    /**
     * Instantiates a new Company catalogue build home.
     *
     * @param browser                the browser
     * @param ftpHost                the ftp host
     * @param ftpPort                the ftp port
     * @param ftpUser                the ftp user
     * @param ftpPassword            the ftp password
     * @param remoteDirApp           the remote dir app
     * @param urlDirApp              the url dir app
     * @param appSkeletonZipPath     the app skeleton zip path
     * @param deleteRemoteAppIfExist the delete remote app if exist
     * @param homeUrlPanel           the home url panel
     * @param homeUserPanel          the home user panel
     * @param homePasswordPanel      the home password panel
     * @param homeHostingId          the home hosting id
     * @param databaseNameSuffix     the database name suffix
     * @param databasePassword       the database password
     * @param databaseDescription    the database description
     * @param databaseHost           the database host
     * @param databasePort           the database port
     * @param appEmailAdministrator  the app email administrator
     */
    public CompanyCatalogueBuildHome(Browser browser, String ftpHost, String ftpPort, String ftpUser, String ftpPassword, String remoteDirApp, String urlDirApp, String appSkeletonZipPath, Boolean deleteRemoteAppIfExist,
                                     String homeUrlPanel, String homeUserPanel, String homePasswordPanel, String homeHostingId, String databaseNameSuffix, String databasePassword, String databaseDescription, String databaseHost, String databasePort,
                                     String appEmailAdministrator, String importSqlTemplateFile, String wpConfigTemplateFile) {
        this.browser = browser;
        this.ftpHost = ftpHost;
        this.ftpPort = ftpPort;
        this.ftpUser = ftpUser;
        this.ftpPassword = ftpPassword;
        this.remoteDirApp = remoteDirApp;
        this.urlDirApp = urlDirApp;
        this.appSkeletonZipPath = appSkeletonZipPath;
        this.deleteRemoteAppIfExist = deleteRemoteAppIfExist;
        this.homeUrlPanel = homeUrlPanel;
        this.homeUserPanel = homeUserPanel;
        this.homePasswordPanel = homePasswordPanel;
        this.homeHostingId = homeHostingId;
        this.databaseNameSuffix = databaseNameSuffix;
        this.databasePassword = databasePassword;
        this.databaseDescription = databaseDescription;
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.appEmailAdministrator = appEmailAdministrator;
        this.importSqlTemplateFile = importSqlTemplateFile;
        this.wpConfigTemplateFile = wpConfigTemplateFile;
    }

    @Override
    public void launch() throws SequenceCompanyCatalogueException {
        try {
            IStepCompanyCatalogue insertDomainHome = new InsertDomainManual(this.ftpHost, Integer.parseInt(this.ftpPort), this.ftpUser, this.ftpPassword, this.remoteDirApp, this.urlDirApp);
            insertDomainHome.launchStep();
            IStepCompanyCatalogue unzipSkeletonFtp = new UnzipSkeletonFtp(this.ftpHost, Integer.parseInt(this.ftpPort), this.ftpUser, this.ftpPassword, true, this.remoteDirApp, this.urlDirApp, new File(this.appSkeletonZipPath), this.deleteRemoteAppIfExist);
            unzipSkeletonFtp.launchStep();
            IStepCompanyCatalogue newDatabaseHome = new NewDatabaseHome(this.browser, this.homeUrlPanel, this.homeUserPanel, this.homePasswordPanel, this.homeHostingId, this.databaseNameSuffix, this.databasePassword, this.databaseDescription, this.deleteRemoteAppIfExist);
            newDatabaseHome.launchStep();
            IStepCompanyCatalogue importSqlHome = new ImportSqlHome(this.homeUserPanel, this.homeHostingId, this.databaseNameSuffix, this.databasePassword, this.urlDirApp, this.databaseHost, Integer.parseInt(this.databasePort), this.appEmailAdministrator, new File(this.importSqlTemplateFile));
            importSqlHome.launchStep();
            IStepCompanyCatalogue configFileWordpress = new ConfigFileWordpress(this.ftpHost, Integer.parseInt(this.ftpPort), this.ftpUser, this.ftpPassword, this.remoteDirApp, this.urlDirApp, this.databaseHost, this.homeHostingId + "_" + this.databaseNameSuffix, this.homeHostingId + "_" + this.databaseNameSuffix, this.databasePassword, new File(this.wpConfigTemplateFile));
            configFileWordpress.launchStep();
            IStepCompanyCatalogue addContentWordpress = new AddContentWordpress(this.databaseHost, this.homeHostingId + "_" + this.databaseNameSuffix, this.homeHostingId + "_" + this.databaseNameSuffix, this.databasePassword);
            addContentWordpress.launchStep();
        } catch (Exception e) {
            throw new SequenceCompanyCatalogueException(e);
        }
    }
}
