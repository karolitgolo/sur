package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build;

import Interfaces.IWithBrowserTest;
import org.junit.rules.TemporaryFolder;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step1UnzipSkeleton.Classes.UnzipSkeletonFtpTest;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step2NewDatabase.Classes.NewDatabaseHomeTest;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step3ImportSql.Classes.ImportSqlHomeTest;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step4InsertDomain.Classes.InsertDomainManualTest;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step5ConfigFile.Classes.ConfigFileWordpressTest;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step6AddContent.Classes.AddContentWordpressTest;
import pl.itgolo.libs.chromium.Browser;

import java.util.Arrays;
import java.util.List;

/**
 * The type Company catalogue build home test.
 */
public class CompanyCatalogueBuildHomeTest {

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
                new UnzipSkeletonFtpTest(browser, temporaryFolder),
                new NewDatabaseHomeTest(browser, temporaryFolder),
                new ImportSqlHomeTest(browser, temporaryFolder),
                new InsertDomainManualTest(browser, temporaryFolder),
                new ConfigFileWordpressTest(browser, temporaryFolder),
                new AddContentWordpressTest(browser, temporaryFolder)
        );
    }
}
