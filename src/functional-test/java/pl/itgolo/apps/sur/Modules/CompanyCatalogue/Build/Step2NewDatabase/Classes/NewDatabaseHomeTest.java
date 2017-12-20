package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step2NewDatabase.Classes;

import Abstracts.WithBrowserTest;
import Interfaces.IWithBrowserTest;
import org.junit.rules.TemporaryFolder;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.libs.chromium.Browser;

/**
 * The type New database home test.
 */
public class NewDatabaseHomeTest extends WithBrowserTest implements IWithBrowserTest {


    /**
     * Instantiates a new With browser test.
     *
     * @param browser
     * @throws Exception the exception
     */
    public NewDatabaseHomeTest(Browser browser, TemporaryFolder temporaryFolder) throws Exception {
        super(browser, temporaryFolder);
    }

    @Override
    public void setup() throws Exception {

    }

    @Override
    public void test() throws Exception {
        IStepCompanyCatalogue newDatabaseHome = new NewDatabaseHome(
                browser,
                env.getProperty("TEST_HOME_URL_PANEL"),
                env.getProperty("TEST_HOME_LOGIN"),
                env.getProperty("TEST_HOME_PASSWORD"),
                env.getProperty("TEST_HOME_HOSTING_ID"),
                env.getProperty("TEST_HOME_DATABASE_NAME_SUFFIX"),
                env.getProperty("TEST_HOME_DATABASE_PASSWORD"),
                env.getProperty("TEST_HOME_DATABASE_DESCRIPTION"),
                true
        );

        newDatabaseHome.launchStep();
    }

    @Override
    public void tearDown() throws Exception {

    }
}
