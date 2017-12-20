package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step4InsertDomain.Classes;

import Abstracts.WithBrowserTest;
import Interfaces.IWithBrowserTest;
import org.junit.rules.TemporaryFolder;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.libs.chromium.Browser;

/**
 * The type New database home test.
 */
public class InsertDomainManualTest extends WithBrowserTest implements IWithBrowserTest {


    /**
     * Instantiates a new With browser test.
     *
     * @param browser
     * @throws Exception the exception
     */
    public InsertDomainManualTest(Browser browser, TemporaryFolder temporaryFolder) throws Exception {
        super(browser, temporaryFolder);
    }

    @Override
    public void setup() throws Exception {

    }

    @Override
    public void test() throws Exception {
        String remoteDirSkeleton = env.getProperty("TEST_REMOTE_DIR_APP");
        String urlDirSkeleton = env.getProperty("TEST_APP_URL_DIR");
        IStepCompanyCatalogue insertDomainHome = new InsertDomainManual(
                env.getProperty("TEST_FTP_HOST"),
                Integer.parseInt(env.getProperty("TEST_FTP_PORT")),
                env.getProperty("TEST_FTP_USER"),
                env.getProperty("TEST_FTP_PASSWORD"),
                remoteDirSkeleton,
                urlDirSkeleton
        );
        insertDomainHome.launchStep();
    }

    @Override
    public void tearDown() throws Exception {

    }
}
