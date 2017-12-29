package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Repairs.RemoveJavaScriptSlimStat.Classes;

import org.apache.commons.text.StringEscapeUtils;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.CompanyCatalogueUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.Commons.Abstracts.ActionSites;
import pl.itgolo.libs.chromium.Actions.NavigateToAction;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

/**
 * The type Remove java script slim stat.
 */
public class RemoveJavaScriptSlimStat extends ActionSites {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        RemoveJavaScriptSlimStat removeJavaScriptSlimStat = new RemoveJavaScriptSlimStat();
        removeJavaScriptSlimStat.launch();
    }

    @Override
    protected void action() throws Exception {
        for (String site : this.sites) {
            site = "http://" + site;
            LogService.debug(site);
            CompanyCatalogueUtils.authorization(this.env.getProperty("NEW_APP_APP_EMAIL_ADMINISTRATOR"), this.env.getProperty("NEW_APP_APP_PASSWORD_ADMINISTRATOR"), site, this.browser);
            String relativeLinks = FileUtils.readFromResource("/pl/itgolo/apps/sur/Modules/CompanyCatalogue/Repairs/RemoveJavaScriptSlimStat/Resources/relativeLinksJavaScripts.txt");
            this.browser.actions.navigateTo(site + "/wp-admin/admin.php?page=allemechanik_theme_front&tab=main");

            String script = String.format("document.querySelectorAll('%1$s')[2].innerHTML=\"%2$s\";", "#tab-extractors-postbox-2 textarea", relativeLinks.replaceAll("\\n","\\\\n").replaceAll("\\r",""));
            this.browser.getJFrame().cefBrowser.executeJavaScript(script, "", 1);
            NavigateToAction.sleep(5000);
            String contentValidate = this.browser.actions.getElement("textarea[name=minify_assets_script_links_footer]").text();
            contentValidate = StringEscapeUtils.unescapeHtml4(contentValidate).replaceAll("\\n", "\r\n");
            if (!contentValidate.equals(relativeLinks)){
                throw new ChromiumException("Failed validate textarea");
            }

            this.browser.actions.clickReload("#tab-extractors-postbox-2 #submit", 60);
            this.browser.actions.waitElement(".notice.notice-success", 20);
            this.browser.actions.navigateTo(site + "/wp-admin/admin.php?page=allemechanik_theme_front&tab=main");
            this.browser.actions.clickReload("#tab-extractors-postbox-1 #submit", 60);
            this.browser.actions.waitElement(".notice.notice-success", 20);
            this.browser.actions.navigateTo(site);

        }
    }
}
