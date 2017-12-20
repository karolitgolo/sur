package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig;

import Interfaces.IWithBrowserTest;
import org.junit.rules.TemporaryFolder;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step6StatConfig.Classes.StatConfigAnalyticsTest;
import pl.itgolo.libs.chromium.Browser;

import java.util.Arrays;
import java.util.List;

/**
 * The type Company catalogue build home test.
 */
public class CompanyCataloguePreConfigTest {

    /**
     * Gets steps test.
     *
     * @param browser         the browser
     * @param temporaryFolder the temporary folder
     * @return the steps test
     * @throws Exception the exception
     */
    public static List<IWithBrowserTest> getStepsTest(Browser browser, TemporaryFolder temporaryFolder) throws Exception {
        return Arrays.asList(
               //new InsertAssetWordpressTest(browser, temporaryFolder),
                //new SmtpConfigGmailTest(browser, temporaryFolder),
                //new FrontConfigWordpressTest(browser, temporaryFolder),
                //new SeoConfigWordpressTest(browser, temporaryFolder),
                //new AdsConfigAdsenseTest(browser, temporaryFolder),
                new StatConfigAnalyticsTest(browser, temporaryFolder)
        );
    }
}
