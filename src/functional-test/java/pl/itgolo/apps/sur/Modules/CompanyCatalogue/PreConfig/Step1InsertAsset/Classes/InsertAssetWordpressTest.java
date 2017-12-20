package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step1InsertAsset.Classes;

import Abstracts.WithBrowserTest;
import Interfaces.IWithBrowserTest;
import org.apache.commons.net.ftp.FTP;
import org.junit.rules.TemporaryFolder;
import org.zeroturnaround.zip.ZipUtil;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Transporters.FtpTransporter;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
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
public class InsertAssetWordpressTest extends WithBrowserTest implements IWithBrowserTest {

    /**
     * Instantiates a new With browser test.
     *
     * @param browser
     * @throws Exception the exception
     */
    public InsertAssetWordpressTest(Browser browser, TemporaryFolder temporaryFolder) throws Exception {
       super(browser, temporaryFolder);
    }

    @Override
    public void setup() throws Exception {

    }

    @Override
    public void test() throws Exception {
        String remoteDirSkeleton = env.getProperty("TEST_REMOTE_DIR_APP");
        String urlDirSkeleton = env.getProperty("TEST_APP_URL_DIR");
        LogService.test("Launch delete image 'bg-search-query_[...].jpg");
        FtpTransporter ftpTransporter = new FtpTransporter(env.getProperty("TEST_FTP_HOST"), Integer.parseInt(env.getProperty("TEST_FTP_PORT")), env.getProperty("TEST_FTP_USER"), env.getProperty("TEST_FTP_PASSWORD"), true, FTP.BINARY_FILE_TYPE);
        ftpTransporter.getFtpClient().deleteFile(remoteDirSkeleton + "/wp-content/plugins/allemechanik/modules/themes/assets/images/" + InsertAssetWordpress.getImageBgSearchQueryNameRemote(urlDirSkeleton));
        LogService.test("Finish delete image 'bg-search-query_[...].jpg");
        IStepCompanyCatalogue insertAssetWordpress = new InsertAssetWordpress(
                env.getProperty("TEST_FTP_HOST"),
                Integer.parseInt(env.getProperty("TEST_FTP_PORT")),
                env.getProperty("TEST_FTP_USER"),
                env.getProperty("TEST_FTP_PASSWORD"),
                remoteDirSkeleton,
                urlDirSkeleton
        );
        insertAssetWordpress.launchStep();
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