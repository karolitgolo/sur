import Interfaces.IWithBrowserTest;
import org.cef.CefApp;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Classes.LogOutConsole;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Interfaces.ILogOut;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.CompanyCataloguePreConfigTest;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;
import pl.itgolo.libs.chromium.Scenes.ConfigurationBrowser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 15.12.2017
 * Time: 20:16
 * Project name: sur
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class AllWithBrowserTest {

    /**
     * The Temp dir.
     */
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    List<IWithBrowserTest> tests;

    Browser browser;

    private void setTests() throws Exception {
        //tests.addAll(CompanyCatalogueBuildHomeTest.getStepsTest(browser, temporaryFolder));
        tests.addAll(CompanyCataloguePreConfigTest.getStepsTest(browser, temporaryFolder));
    }

    @Test
    public void test() throws Exception {
        LogService.logOuts.addAll(Arrays.asList((ILogOut) new LogOutConsole()));
        this.tests = new ArrayList<>();
        LogService.test("Before initialize browser");
        initializeBrowser();
        LogService.test("After initialized browser");
        setTests();
        for (IWithBrowserTest test : tests) {
            test.setup();
        }
        for (IWithBrowserTest test : tests) {
            test.test();
        }
        for (IWithBrowserTest test : tests) {
            test.tearDown();
        }
        closeBrowser();
    }

    private void initializeBrowser() throws ChromiumException {
        ConfigurationBrowser config = new ConfigurationBrowser();
        config.setEnableGeolocation(false);
        config.setEnableJSDialog(false);
        browser = new Browser(config);
    }

    private void closeBrowser() {
        browser.getJFrame().cefApp.shutdownFix();
        CefApp.getInstance().dispose();
    }
}
