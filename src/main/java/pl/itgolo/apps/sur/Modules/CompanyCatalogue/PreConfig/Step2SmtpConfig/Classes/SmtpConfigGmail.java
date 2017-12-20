package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step2SmtpConfig.Classes;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.CompanyCatalogueUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.Google.Classes.GoogleApiCredential;
import pl.itgolo.apps.sur.Modules.Commons.Utils.Google.GoogleDevConsoleUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step2SmtpConfig.Abstracts.SmtpConfig;
import pl.itgolo.libs.chromium.Actions.NavigateToAction;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

import java.net.URL;

/**
 * The type Import sql home.
 */
public class SmtpConfigGmail extends SmtpConfig {


    private final String wordpressLogin;
    private final String wordpressPassword;
    private String urlDirSkeleton;
    private String prettyAppUrl;
    private final String googleDevLogin;
    private final String googleDevPassword;
    private final String googleDevProjectName;
    private String appMainTitle;
    private Browser browser;
    private String apiCredentialIdClient;
    private String apiCredentialSecretKey;
    private boolean omittedStep;

    public SmtpConfigGmail(String wordpressLogin, String wordpressPassword, String urlDirSkeleton, String prettyAppUrl, String googleDevLogin, String googleDevPassword, String googleDevProjectName, String appMainTitle, Browser browser) {
        this.wordpressLogin = wordpressLogin;
        this.wordpressPassword = wordpressPassword;
        this.urlDirSkeleton = urlDirSkeleton;
        this.prettyAppUrl = prettyAppUrl;
        this.googleDevLogin = googleDevLogin;
        this.googleDevPassword = googleDevPassword;
        this.googleDevProjectName = googleDevProjectName;
        this.appMainTitle = appMainTitle;
        this.browser = browser;
        this.omittedStep = false;
    }

    @Override
    protected void before() throws StepCompanyCatalogueException {
        try {
            CompanyCatalogueUtils.authorization(this.wordpressLogin, this.wordpressPassword, this.urlDirSkeleton, this.browser);

            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/options-general.php?page=gmail-smtp-settings");
            this.browser.actions.fillInput("#from_name", this.prettyAppUrl);
            this.browser.actions.fillInput("#oauth_user_email", this.googleDevLogin);
            this.browser.actions.fillInput("#from_email", this.googleDevLogin);
            this.browser.actions.clickReload("#gmail_smtp_update_settings", 60);
            this.browser.actions.waitElement(".updated.fade", 20);

            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/options-general.php?page=gmail-smtp-settings&action=test-email", 60);
            this.browser.actions.fillInput("#gmail_smtp_to_email", this.googleDevLogin);
            this.browser.actions.fillInput("#gmail_smtp_email_subject", this.prettyAppUrl);
            this.browser.actions.fillTextArea("#gmail_smtp_email_body", "test");
            this.browser.actions.clickReload("#gmail_smtp_send_test_email", 60);
            this.omittedStep = this.browser.actions.getSource().contains("CLIENT: 221 2.0.0 closing connection");
            if (this.omittedStep){
                return;
            }
            String labelName = String.format("%1$s CompanyCatalogue SMTP", new URL(this.urlDirSkeleton).getHost());
            for (Integer i = 0; i < 100; i++) {
                try {
                    GoogleApiCredential googleApiCredential = GoogleDevConsoleUtils.getApiCredentialByLabelName(labelName, this.googleDevLogin, this.googleDevPassword, this.googleDevProjectName, this.browser);
                    this.apiCredentialIdClient = googleApiCredential.getIdClient();
                    this.apiCredentialSecretKey = googleApiCredential.getSecretKey();
                    return;
                } catch (ChromiumException e) {
                    // this exception is not require service
                    // because only wait for get API credentials
                    LogService.debug(String.format("Wait for API credential label name: '%1$s' of project: '%2$s' for login Google: '%3$s'", labelName, this.googleDevProjectName, this.googleDevLogin));
                    NavigateToAction.sleep(3000);
                }
            }
            throw new StepCompanyCatalogueException(String.format("Not found API credential label name: '%1$s' of project: '%2$s' for login Google: '%3$s'", labelName, this.googleDevProjectName, this.googleDevLogin));
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean setConfiguration() throws StepCompanyCatalogueException {
        if (this.omittedStep){
            return true;
        }
        try {
            CompanyCatalogueUtils.authorization(this.wordpressLogin, this.wordpressPassword, this.urlDirSkeleton, this.browser);
            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/options-general.php?page=gmail-smtp-settings&action=revoke-access");
            this.browser.actions.clickReload("#gmail_smtp_delete_access_key", 60);
            this.browser.actions.waitElement(".updated.fade", 20);
            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/options-general.php?page=gmail-smtp-settings");
            this.browser.actions.fillInput("#oauth_client_id", this.apiCredentialIdClient);
            this.browser.actions.fillInput("#oauth_client_secret", this.apiCredentialSecretKey);
            this.browser.actions.fillInput("#from_name", this.prettyAppUrl);
            this.browser.actions.fillInput("#oauth_user_email", this.googleDevLogin);
            this.browser.actions.fillInput("#from_email", this.googleDevLogin);
            this.browser.actions.clickReload("#gmail_smtp_update_settings", 60);
            this.browser.actions.waitElement(".updated.fade", 20);
            return true;
        } catch (ChromiumException e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateSetConfiguration() throws StepCompanyCatalogueException {
        if (this.omittedStep){
            return true;
        }
        for (Integer i= 0; i<100 ; i++){
            try {
                LogService.debug("Grant permission for Google Dev Console and switch to test tab");
                String source = this.browser.actions.getSource(60, 5, 200);
                if (source.contains("gmail_smtp_to_email")){
                    this.browser.actions.fillInput("#gmail_smtp_to_email", this.googleDevLogin);
                    this.browser.actions.fillInput("#gmail_smtp_email_subject", this.prettyAppUrl);
                    this.browser.actions.fillTextArea("#gmail_smtp_email_body", "test");
                    this.browser.actions.clickReload("#gmail_smtp_send_test_email", 60);
                    if (!this.browser.actions.getSource().contains("CLIENT: 221 2.0.0 closing connection")){
                        throw new StepCompanyCatalogueException(String.format("Not found in source code: '%1$s' - try generate new credential API in Google dev console", "CLIENT: 221 2.0.0 closing connection"));
                    } else {
                        return true;
                    }
                }
                NavigateToAction.sleep(2000);
            } catch (ChromiumException e) {
                throw new StepCompanyCatalogueException(e);
            }
        }
        return false;
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {
        if (this.omittedStep){
            return;
        }
        try {
            NavigateToAction.sleep(20000);
        } catch (ChromiumException e) {
            e.printStackTrace();
        }
    }
}
