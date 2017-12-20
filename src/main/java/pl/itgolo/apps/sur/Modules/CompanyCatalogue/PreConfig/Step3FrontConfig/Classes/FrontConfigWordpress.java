package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step3FrontConfig.Classes;

import org.jsoup.nodes.Element;
import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Utils.CompanyCatalogueUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step3FrontConfig.Abstracts.FrontConfig;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

/**
 * The type Import sql home.
 */
public class FrontConfigWordpress extends FrontConfig {


    private final String wordpressLogin;
    private final String wordpressPassword;
    private String urlDirSkeleton;
    private String appMainTitle;
    private Browser browser;

    public FrontConfigWordpress(String wordpressLogin, String wordpressPassword, String urlDirSkeleton, String appMainTitle, Browser browser) {
        this.wordpressLogin = wordpressLogin;
        this.wordpressPassword = wordpressPassword;
        this.urlDirSkeleton = urlDirSkeleton;
        this.appMainTitle = appMainTitle;
        this.browser = browser;
    }

    @Override
    protected void before() throws StepCompanyCatalogueException {


    }

    @Override
    protected Boolean setConfiguration() throws StepCompanyCatalogueException {
        try {
            CompanyCatalogueUtils.authorization(this.wordpressLogin, this.wordpressPassword, this.urlDirSkeleton, this.browser);
            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/admin.php?page=allemechanik_theme_front&tab=subtitles");
            this.browser.actions.fillInput("input[name=set_subtitles_main_name]", this.appMainTitle);
            this.browser.actions.clickReload("#tab-extractors-postbox-0 #submit", 60);
            this.browser.actions.waitElement(".notice.notice-success", 20);
            return true;
        } catch (ChromiumException e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateSetConfiguration() throws StepCompanyCatalogueException {
        try {
            this.browser.actions.navigateTo(this.urlDirSkeleton, 60);
            Element siteTitleElement = this.browser.actions.getElement(".site-title");
            if (!siteTitleElement.html().contains(this.appMainTitle)){
                throw new StepCompanyCatalogueException("Failed validate set configuration 'app main title'");
            }
            return true;
        } catch (ChromiumException e) {
           throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {

    }
}
