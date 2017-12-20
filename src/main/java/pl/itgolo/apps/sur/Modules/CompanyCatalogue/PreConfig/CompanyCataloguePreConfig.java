package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.SequenceCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.ISequenceCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step1InsertAsset.Classes.InsertAssetWordpress;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step2SmtpConfig.Classes.SmtpConfigGmail;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step3FrontConfig.Classes.FrontConfigWordpress;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step4SeoConfig.Classes.SeoConfigWordpress;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step5AdsConfg.Classes.AdsConfigAdsense;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step6StatConfig.Classes.StatConfigAnalytics;
import pl.itgolo.libs.chromium.Browser;

import java.io.File;

/**
 * The type Company catalogue pre config.
 */
public class CompanyCataloguePreConfig implements ISequenceCompanyCatalogue {

    private Browser browser;
    private String ftpHost;
    private String ftpPort;
    private final String ftpUser;
    private final String ftpPassword;
    private String remoteDirApp;
    private String urlDirApp;
    private String appEmailAdministrator;
    private String appPasswordAdministrator;
    private final String prettyAppUrl;
    private final String googleDevLogin;
    private final String googleDevPassword;
    private final String googleDevProjectName;
    private final String appMainTitle;
    private final String adsense1File;
    private final String adsense2File;
    private final String adsense3File;
    private final String adsense4File;
    private final String adsense5File;

    /**
     * Instantiates a new Company catalogue pre config.
     *
     * @param browser                  the browser
     * @param ftpHost                  the ftp host
     * @param ftpPort                  the ftp port
     * @param ftpUser                  the ftp user
     * @param ftpPassword              the ftp password
     * @param remoteDirApp             the remote dir app
     * @param urlDirApp                the url dir app
     * @param appEmailAdministrator    the app email administrator
     * @param appPasswordAdministrator the app password administrator
     * @param prettyAppUrl             the pretty app url
     * @param googleDevLogin           the google dev login
     * @param googleDevPassword        the google dev password
     * @param googleDevProjectName     the google dev project name
     * @param appMainTitle             the app main title
     * @param adsense1File             the adsense 1 file
     * @param adsense2File             the adsense 2 file
     * @param adsense3File             the adsense 3 file
     * @param adsense4File             the adsense 4 file
     * @param adsense5File             the adsense 5 file
     */
    public CompanyCataloguePreConfig(Browser browser, String ftpHost, String ftpPort, String ftpUser, String ftpPassword, String remoteDirApp, String urlDirApp,
                                     String appEmailAdministrator, String appPasswordAdministrator, String prettyAppUrl, String googleDevLogin, String googleDevPassword, String googleDevProjectName, String appMainTitle,
                                     String adsense1File, String adsense2File, String adsense3File, String adsense4File, String adsense5File) {
        this.browser = browser;
        this.ftpHost = ftpHost;
        this.ftpPort = ftpPort;
        this.ftpUser = ftpUser;
        this.ftpPassword = ftpPassword;
        this.remoteDirApp = remoteDirApp;
        this.urlDirApp = urlDirApp;
        this.appEmailAdministrator = appEmailAdministrator;
        this.appPasswordAdministrator = appPasswordAdministrator;
        this.prettyAppUrl = prettyAppUrl;
        this.googleDevLogin = googleDevLogin;
        this.googleDevPassword = googleDevPassword;
        this.googleDevProjectName = googleDevProjectName;
        this.appMainTitle = appMainTitle;
        this.adsense1File = adsense1File;
        this.adsense2File = adsense2File;
        this.adsense3File = adsense3File;
        this.adsense4File = adsense4File;
        this.adsense5File = adsense5File;
    }

    @Override
    public void launch() throws SequenceCompanyCatalogueException {
        try {
            IStepCompanyCatalogue insertAssetWordpress = new InsertAssetWordpress(this.ftpHost, Integer.parseInt(this.ftpPort), this.ftpUser, this.ftpPassword, this.remoteDirApp, this.urlDirApp);
            insertAssetWordpress.launchStep();
            IStepCompanyCatalogue smtpConfigGmail = new SmtpConfigGmail(this.appEmailAdministrator, this.appPasswordAdministrator, this.urlDirApp, this.prettyAppUrl, this.googleDevLogin, this.googleDevPassword, this.googleDevProjectName, this.appMainTitle, this.browser);
            smtpConfigGmail.launchStep();
            IStepCompanyCatalogue frontConfigWordpress = new FrontConfigWordpress(this.appEmailAdministrator, this.appPasswordAdministrator, this.urlDirApp, this.appMainTitle, this.browser);
            frontConfigWordpress.launchStep();
            IStepCompanyCatalogue seoConfigWordpress = new SeoConfigWordpress(this.appEmailAdministrator, this.appPasswordAdministrator, this.urlDirApp, this.browser);
            seoConfigWordpress.launchStep();
            IStepCompanyCatalogue adsConfigAdsense = new AdsConfigAdsense(this.appEmailAdministrator, this.appPasswordAdministrator, this.urlDirApp, this.browser, new File(this.adsense1File), new File(this.adsense2File), new File(this.adsense3File), new File(this.adsense4File), new File(this.adsense5File));
            adsConfigAdsense.launchStep();
            IStepCompanyCatalogue statConfigAnalytics = new StatConfigAnalytics(this.appEmailAdministrator, this.appPasswordAdministrator, this.urlDirApp, this.browser);
            statConfigAnalytics.launchStep();
        } catch (Exception e) {
            throw new SequenceCompanyCatalogueException(e);
        }
    }
}
