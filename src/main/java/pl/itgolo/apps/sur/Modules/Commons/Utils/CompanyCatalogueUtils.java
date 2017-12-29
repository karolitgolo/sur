package pl.itgolo.apps.sur.Modules.Commons.Utils;

import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

/**
 * The type Company catalogue utils.
 */
public class CompanyCatalogueUtils {

    /**
     * Authorization.
     *
     * @param username       the username
     * @param password       the password
     * @param urlDirSkeleton the url dir skeleton
     * @param browser        the browser
     * @throws ChromiumException the chromium exception
     */
    public static void authorization(String username, String password, String urlDirSkeleton, Browser browser) throws ChromiumException {
        browser.actions.navigateTo(urlDirSkeleton + "/wp-admin", 60);
        if (!BrowserUtils.existElement("#menu-dashboard", browser)){
            browser.actions.navigateTo(urlDirSkeleton + "/logowanie/");
            browser.actions.fillInput("#email", username);
            browser.actions.fillInput("#password", password);
            browser.actions.clickReload(".jumbotron button[type=submit]", 60);
            browser.actions.navigateTo(urlDirSkeleton + "/wp-admin", 60);
            if (!BrowserUtils.existElement("#menu-dashboard", browser)){
                throw new ChromiumException("Can not authorized to admin panel for user: " + username);
            }
        }
    }
}
