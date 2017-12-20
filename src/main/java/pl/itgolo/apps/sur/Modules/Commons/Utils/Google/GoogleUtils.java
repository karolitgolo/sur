package pl.itgolo.apps.sur.Modules.Commons.Utils.Google;

import pl.itgolo.apps.sur.Modules.Commons.Utils.BrowserUtils;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

/**
 * The type Google utils.
 */
public class GoogleUtils {

    /**
     * Login to developer console.
     *
     * @param login    the login
     * @param password the password
     * @param browser  the browser
     * @throws ChromiumException the chromium exception
     */
    public static void authorization(String login, String password, Browser browser) throws ChromiumException {
        browser.actions.navigateTo("https://accounts.google.com/ServiceLogin/identifier", 60);
        if (!browser.actions.getSource().contains("(" + login + ")")) {
            browser.actions.navigateTo("https://accounts.google.com/Logout", 60);
        } else {
            return;
        }
        browser.actions.navigateTo("https://accounts.google.com/ServiceLogin/identifier", 60);
        if (!BrowserUtils.existElement("#identifierId", browser)) {
            throw new ChromiumException("Can not found field for email address of login");
        }
        browser.actions.fillInput("#identifierId", login);
        browser.actions.click("#identifierNext");
        browser.actions.waitElement("input[name=password]", 20);
        browser.actions.fillInput("input[name=password]", password);
        browser.actions.clickReload("#passwordNext", 60);
       browser.actions.waitElement(".gb_ab", 45);
        if (!browser.actions.getSource().contains("(" + login + ")")) {
            throw new ChromiumException("Not authorization to google developer");
        }
    }
}
