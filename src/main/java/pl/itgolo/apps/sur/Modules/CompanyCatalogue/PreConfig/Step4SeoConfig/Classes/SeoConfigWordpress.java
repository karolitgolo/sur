package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step4SeoConfig.Classes;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Utils.CompanyCatalogueUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step4SeoConfig.Abstracts.SeoConfig;
import pl.itgolo.libs.chromium.Browser;

/**
 * The type Import sql home.
 */
public class SeoConfigWordpress extends SeoConfig {


    private final String wordpressLogin;
    private final String wordpressPassword;
    private String urlDirSkeleton;
    private Browser browser;

    public SeoConfigWordpress(String wordpressLogin, String wordpressPassword, String urlDirSkeleton, Browser browser) {
        this.wordpressLogin = wordpressLogin;
        this.wordpressPassword = wordpressPassword;
        this.urlDirSkeleton = urlDirSkeleton;
        this.browser = browser;
    }

    @Override
    protected void before() throws StepCompanyCatalogueException {


    }

    @Override
    protected Boolean setConfiguration() throws StepCompanyCatalogueException {
        try {
            CompanyCatalogueUtils.authorization(this.wordpressLogin, this.wordpressPassword, this.urlDirSkeleton, this.browser);
            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/admin.php?page=allemechanik_theme_front&tab=seo");
            String seoDescriptionTemplate = FileUtils.readFromResource("/pl/itgolo/apps/sur/Modules/CompanyCatalogue/PreConfig/Step4SeoConfig/Resources/seoDescriptionTemplate.txt");
            this.browser.actions.fillTextArea("textarea[name=seo_metadesc_template]", seoDescriptionTemplate);
            this.browser.actions.clickReload("#tab-extractors-postbox-0 #submit", 60);
            this.browser.actions.waitElement(".notice.notice-success", 20);
            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/admin.php?page=wpseo_titles#top#post-types");
            String seoTitleTemplate = FileUtils.readFromResource("/pl/itgolo/apps/sur/Modules/CompanyCatalogue/PreConfig/Step4SeoConfig/Resources/seoTitleTemplate.txt");
            this.browser.actions.fillInput("#title-post", seoTitleTemplate);
            this.browser.actions.clickReload("#wpseo_content_top #submit", 60);
            this.browser.actions.waitElement("#setting-error-settings_updated", 20);
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateSetConfiguration() throws StepCompanyCatalogueException {
        // validate in method setConfiguration
        return true;
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {

    }
}
