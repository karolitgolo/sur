package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step3ImportSql.Classes;

import Abstracts.WithBrowserTest;
import Interfaces.IWithBrowserTest;
import org.junit.rules.TemporaryFolder;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.libs.chromium.Browser;

import java.io.File;

/**
 * The type New database home test.
 */
public class ImportSqlHomeTest extends WithBrowserTest implements IWithBrowserTest {


    /**
     * Instantiates a new With browser test.
     *
     * @param browser
     * @throws Exception the exception
     */
    public ImportSqlHomeTest(Browser browser, TemporaryFolder temporaryFolder) throws Exception {
        super(browser, temporaryFolder);
    }

    @Override
    public void setup() throws Exception {

    }

    @Override
    public void test() throws Exception {
        IStepCompanyCatalogue importSqlHome = new ImportSqlHome(
                env.getProperty("TEST_HOME_LOGIN"),
                env.getProperty("TEST_HOME_HOSTING_ID"),
                env.getProperty("TEST_HOME_DATABASE_NAME_SUFFIX"),
                env.getProperty("TEST_HOME_DATABASE_PASSWORD"),
                env.getProperty("TEST_APP_URL_DIR"),
                env.getProperty("TEST_HOME_DATABASE_HOST"),
                Integer.parseInt(env.getProperty("TEST_HOME_DATABASE_PORT")),
                env.getProperty("TEST_APP_EMAIL_ADMINISTRATOR"),
                new File(env.getProperty("TEST_RESOURCE_FILE_IMPORT_SQL_TEMPLATE"))
        );
        importSqlHome.launchStep();
    }

    @Override
    public void tearDown() throws Exception {

    }
}
