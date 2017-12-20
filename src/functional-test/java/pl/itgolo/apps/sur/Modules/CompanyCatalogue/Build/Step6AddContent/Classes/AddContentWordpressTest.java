package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step6AddContent.Classes;

import Abstracts.WithBrowserTest;
import Interfaces.IWithBrowserTest;
import org.junit.rules.TemporaryFolder;
import org.zeroturnaround.zip.ZipUtil;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step5ConfigFile.Classes.ConfigFileWordpress;
import pl.itgolo.libs.chromium.Browser;

import java.io.File;
import java.io.IOException;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 15.12.2017
 * Time: 15:52
 * Project name: sur
 *
 * @author Karol Golec karol.itgolo@gmail.com
 */
public class AddContentWordpressTest extends WithBrowserTest implements IWithBrowserTest {

    /**
     * Instantiates a new With browser test.
     *
     * @param browser
     * @throws Exception the exception
     */
    public AddContentWordpressTest(Browser browser, TemporaryFolder temporaryFolder) throws Exception {
       super(browser, temporaryFolder);
    }

    @Override
    public void setup() throws Exception {

    }

    @Override
    public void test() throws Exception {
        String databaseNameFull = env.getProperty("TEST_HOME_HOSTING_ID") + "_" + env.getProperty("TEST_HOME_DATABASE_NAME_SUFFIX");
        String databaseUser = env.getProperty("TEST_HOME_HOSTING_ID") + "_" + env.getProperty("TEST_HOME_DATABASE_NAME_SUFFIX");
        IStepCompanyCatalogue addContentWordpress = new AddContentWordpress(
                env.getProperty("TEST_HOME_DATABASE_HOST"),
                databaseNameFull,
                databaseUser,
                env.getProperty("TEST_HOME_DATABASE_PASSWORD")
        );
        addContentWordpress.launchStep();
    }

    @Override
    public void tearDown() throws Exception {

    }

    private File createTempSkeletonZipFile() throws IOException {
        LogService.test("Create temp directory");
        File dirSkeleton = temporaryFolder.newFolder("UnzipSkeletonFtp");
        File dirWithZipSkeleton = temporaryFolder.newFolder("UnzipSkeletonFtpZip");
        File subDir1Skeleton= temporaryFolder.newFolder("UnzipSkeletonFtp", "subDir1");
        File subDir2Skeleton= temporaryFolder.newFolder("UnzipSkeletonFtp", "subDir2");
        FileUtils.write("Example content 1", new File(dirSkeleton, "index.php"));
        FileUtils.write("Example content 2", new File(subDir1Skeleton, "fileExample.php"));
        File tempSkeletonZipFile = new File(dirWithZipSkeleton, "skeleton.zip");
        LogService.test("Compressed to zip");
        ZipUtil.pack(dirSkeleton, tempSkeletonZipFile);
        return tempSkeletonZipFile;
    }

}