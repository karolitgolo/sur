package pl.itgolo.apps.sur.Modules.Commons.Utils;

import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

/**
 * The type Home utils.
 */
public class HomeUtils {

    /**
     * Authorization.
     *
     * @param urlPanel      the url panel
     * @param loginPanel    the login panel
     * @param passwordPanel the password panel
     * @param browser       the browser
     * @throws ChromiumException the chromium exception
     */
    public static void authorization(String urlPanel, String loginPanel, String passwordPanel, Browser browser) throws ChromiumException {
        browser.actions.navigateTo(urlPanel);
        Boolean correctAuthorized = false;
        if (BrowserUtils.existElement("#top-panel-login-name", browser)) {
            if (BrowserUtils.existElement(String.format("#top-panel-login-name:contains(%1$s)", loginPanel), browser)) {
                correctAuthorized = true;
            } else {
                browser.actions.clickReload("#top-panel-logout a:contains(Wyloguj)", 60);
            }
        }
        if (!correctAuthorized) {
            browser.actions.fillInput("input[name='form_user']", loginPanel, 10);
            browser.actions.fillInput("input[name='form_pass']", passwordPanel, 10);
            browser.actions.clickReload("button[type='submit']", 60);
        }
        if (!BrowserUtils.existElement(String.format("#top-panel-login-name:contains(%1$s)", loginPanel), browser)) {
            throw new ChromiumException(String.format("Can not authorized user '%1$s' in url: %2$s", loginPanel, urlPanel));
        }
    }
}
