package pl.itgolo.apps.sur.Modules.Commons.Utils;

import org.jsoup.nodes.Element;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

/**
 * The type Browser utils.
 */
public class BrowserUtils {

    /**
     * Exist element boolean.
     *
     * @param cssSelector the css selector
     * @param browser     the browser
     * @return the boolean
     * @throws ChromiumException the chromium exception
     */
    public static Boolean existElement(String cssSelector, Browser browser) throws ChromiumException {
        Element element = browser.actions.getElement(cssSelector);
        return (element == null) ? false : true;
    }
}
