package pl.itgolo.apps.sur.Modules.Commons.Utils.Google;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.itgolo.apps.sur.Modules.Commons.Utils.Google.Classes.GoogleApiCredential;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

/**
 * The type Google dev console utils.
 */
public class GoogleDevConsoleUtils {

    /**
     * Gets api credential by label name.
     *
     * @param labelName            the label name
     * @param googleDevLogin       the google dev login
     * @param googleDevPassword    the google dev password
     * @param googleDevProjectName the google dev project name
     * @param browser
     * @return the api credential by label name
     */
    public static GoogleApiCredential getApiCredentialByLabelName(String labelName, String googleDevLogin, String googleDevPassword, String googleDevProjectName, Browser browser) throws ChromiumException {
        GoogleUtils.authorization(googleDevLogin, googleDevPassword, browser);
        browser.actions.navigateTo("https://console.developers.google.com/apis/credentials?project=" + googleDevProjectName, 60);
        browser.actions.waitElement(".p6n-table-row-checkbox-enabled", 20);
        for (Element tds : browser.actions.getElements(".p6n-api-credential-table-label")) {
            Element a = tds.select("a").first();
            String innerHtml = a.html();
            if (innerHtml.trim().equalsIgnoreCase(labelName)){
                String linkApiCredential = "https://console.developers.google.com" + a.attr("href");
                browser.actions.navigateTo(linkApiCredential);
                browser.actions.waitElement(".p6n-nowrap.p6n-kv-list-value span", 20);
                Elements values = browser.actions.getElements(".p6n-nowrap.p6n-kv-list-value span");
                String idClient = null;
                String secretKey = null;
                for (Integer i =0 ; i <values.size() ; i++) {
                    if (i.equals(0)){
                        idClient = values.get(0).html().trim();
                    } else if (i.equals(1)){
                        secretKey = values.get(1).html().trim();
                    } else {
                        break;
                    }
                }
                return new GoogleApiCredential(idClient, secretKey);
            }
        }
        throw new ChromiumException(String.format("Not found API credential label name: '%1$s' of project: '%2$s' for login Google: '%3$s'", labelName, googleDevProjectName, googleDevLogin));
    }
}
