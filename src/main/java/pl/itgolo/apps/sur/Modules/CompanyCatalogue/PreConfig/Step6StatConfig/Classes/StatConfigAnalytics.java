package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step6StatConfig.Classes;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BrowserUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.CompanyCatalogueUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step6StatConfig.Abstracts.StatConfig;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

/**
 * The type Import sql home.
 */
public class StatConfigAnalytics extends StatConfig {


    private final String wordpressLogin;
    private final String wordpressPassword;
    private String urlDirSkeleton;
    private Browser browser;

    public StatConfigAnalytics(String wordpressLogin, String wordpressPassword, String urlDirSkeleton, Browser browser) {
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
            this.browser.actions.navigateTo(this.urlDirSkeleton + "/wp-admin/plugins.php");
            if (BrowserUtils.existElement("tr[data-slug=google-analytics-for-wordpress-by-monsterinsights] .deactivate a", this.browser)) {
                this.browser.actions.clickReload("tr[data-slug=google-analytics-for-wordpress-by-monsterinsights] .deactivate a", 60);
            }
            return !BrowserUtils.existElement("tr[data-slug=google-analytics-for-wordpress-by-monsterinsights] .deactivate a", this.browser);
        } catch (ChromiumException e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateSetConfiguration() throws StepCompanyCatalogueException {
        // validate is not required
        // because analytics will be off
        return true;
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {

    }
}
