package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step5AdsConfg.Classes;

import org.apache.commons.text.StringEscapeUtils;
import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BrowserUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.CompanyCatalogueUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step5AdsConfg.Abstracts.AdsConfig;
import pl.itgolo.libs.chromium.Actions.NavigateToAction;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

import java.io.File;

/**
 * The type Import sql home.
 */
public class AdsConfigAdsense extends AdsConfig {


    private final String wordpressLogin;
    private final String wordpressPassword;
    private String urlDirSkeleton;
    private Browser browser;
    private final File contentAd1File;
    private final File contentAd2File;
    private final File contentAd3File;
    private final File contentAd4File;
    private final File contentAd5File;

    public AdsConfigAdsense(String wordpressLogin, String wordpressPassword, String urlDirSkeleton, Browser browser, File contentAd1File, File contentAd2File, File contentAd3File, File contentAd4File, File contentAd5File) {
        this.wordpressLogin = wordpressLogin;
        this.wordpressPassword = wordpressPassword;
        this.urlDirSkeleton = urlDirSkeleton;
        this.browser = browser;
        this.contentAd1File = contentAd1File;
        this.contentAd2File = contentAd2File;
        this.contentAd3File = contentAd3File;
        this.contentAd4File = contentAd4File;
        this.contentAd5File = contentAd5File;
    }

    @Override
    protected void before() throws StepCompanyCatalogueException {


    }

    @Override
    protected Boolean setConfiguration() throws StepCompanyCatalogueException {
        try {
            CompanyCatalogueUtils.authorization(this.wordpressLogin, this.wordpressPassword, this.urlDirSkeleton, this.browser);
            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/admin.php?page=allemechanik_ads");
            String contentAd1 = FileUtils.readFromFile(this.contentAd1File).trim();
            String contentAd2 = FileUtils.readFromFile(this.contentAd2File).trim();
            String contentAd3 = FileUtils.readFromFile(this.contentAd3File).trim();
            String contentAd4 = FileUtils.readFromFile(this.contentAd4File).trim();
            String contentAd5 = FileUtils.readFromFile(this.contentAd5File).trim();
            String script = String.format("document.querySelector('%1$s').innerHTML='%2$s';", "textarea[name=code_source_code]", contentAd1);
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            NavigateToAction.sleep(1000);
            String contentAd1Validate = this.browser.actions.getElement("textarea[name=code_source_code]").html();
            contentAd1Validate = StringEscapeUtils.unescapeHtml4(contentAd1Validate);
            if (!contentAd1Validate.equals(contentAd1)){
                throw new StepCompanyCatalogueException("Failed validate fill textarea ad 1");
            }
            this.browser.actions.clickReload("form:nth-of-type(1) #submit", 60);
            this.browser.actions.waitElement(".notice.notice-success", 20);

            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/admin.php?page=allemechanik_ads");
            script = String.format("document.querySelector('%1$s').innerHTML='%2$s';", "textarea[name=code_source_code_2]", contentAd2);
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            NavigateToAction.sleep(1000);
            String contentAd2Validate = this.browser.actions.getElement("textarea[name=code_source_code_2]").html();
            contentAd2Validate = StringEscapeUtils.unescapeHtml4(contentAd2Validate);
            if (!contentAd2Validate.equals(contentAd2)){
                throw new StepCompanyCatalogueException("Failed validate fill textarea ad 2");
            }
            script = String.format("document.querySelectorAll('input[type=submit]')[1].click()");
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            this.browser.actions.waitElement(".notice.notice-success", 60);

            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/admin.php?page=allemechanik_ads");
            script = String.format("document.querySelector('%1$s').innerHTML='%2$s';", "textarea[name=code_source_code_3]", contentAd3);
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            NavigateToAction.sleep(1000);
            String contentAd3Validate = this.browser.actions.getElement("textarea[name=code_source_code_3]").html();
            contentAd3Validate = StringEscapeUtils.unescapeHtml4(contentAd3Validate);
            if (!contentAd3Validate.equals(contentAd3)){
                throw new StepCompanyCatalogueException("Failed validate fill textarea ad 3");
            }
            script = String.format("document.querySelectorAll('input[type=submit]')[2].click()");
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            this.browser.actions.waitElement(".notice.notice-success", 60);

            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/admin.php?page=allemechanik_ads");
            script = String.format("document.querySelector('%1$s').innerHTML='%2$s';", "textarea[name=code_source_code_5]", contentAd4);
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            NavigateToAction.sleep(1000);
            String contentAd4Validate = this.browser.actions.getElement("textarea[name=code_source_code_5]").html();
            contentAd4Validate = StringEscapeUtils.unescapeHtml4(contentAd4Validate);
            if (!contentAd4Validate.equals(contentAd4)){
                throw new StepCompanyCatalogueException("Failed validate fill textarea ad 4");
            }
            script = String.format("document.querySelectorAll('input[type=submit]')[3].click()");
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            this.browser.actions.waitElement(".notice.notice-success", 60);

            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/admin.php?page=allemechanik_ads");
            script = String.format("document.querySelector('%1$s').innerHTML='%2$s';", "textarea[name=code_source_code_4]", contentAd5);
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            NavigateToAction.sleep(1000);
            String contentAd5Validate = this.browser.actions.getElement("textarea[name=code_source_code_4]").html();
            contentAd5Validate = StringEscapeUtils.unescapeHtml4(contentAd5Validate);
            if (!contentAd5Validate.equals(contentAd5)){
                throw new StepCompanyCatalogueException("Failed validate fill textarea ad 5");
            }
            script = String.format("document.querySelectorAll('input[type=submit]')[4].click()");
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            this.browser.actions.waitElement(".notice.notice-success", 60);
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateSetConfiguration() throws StepCompanyCatalogueException {
        try {
            this.browser.actions.navigateTo(this.urlDirSkeleton);
            return BrowserUtils.existElement(".adsbygoogle iframe", this.browser);
        } catch (ChromiumException e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {

    }
}
