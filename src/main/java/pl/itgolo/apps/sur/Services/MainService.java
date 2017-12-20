package pl.itgolo.apps.sur.Services;

import org.cef.CefApp;
import pl.itgolo.apps.sur.Modules.Commons.Exceptions.SequenceCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Classes.LogOutConsole;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Interfaces.ILogOut;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.CompanyCatalogueBuildHome;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.CompanyCataloguePreConfig;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;
import pl.itgolo.libs.chromium.Scenes.ConfigurationBrowser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Properties;

/**
 * The type Main service.
 */
public class MainService {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws SequenceCompanyCatalogueException the sequence company catalogue exception
     * @throws ChromiumException                 the chromium exception
     * @throws IOException                       the io exception
     */
    public static void main(String[] args) throws SequenceCompanyCatalogueException, ChromiumException, IOException {
        LogService.logOuts.addAll(Arrays.asList((ILogOut) new LogOutConsole()));
        ConfigurationBrowser config = new ConfigurationBrowser();
        config.setEnableGeolocation(false);
        config.setEnableJSDialog(false);
        Browser browser = new Browser(config);
        Properties env = new Properties();
        env.load(new InputStreamReader(new FileInputStream(new File("env.properties")), Charset.forName("UTF-8")));
       // companyCatalogueBuildHome(browser, env);
        companyCataloguePreConfig(browser, env);
        browser.getJFrame().cefApp.shutdownFix();
        CefApp.getInstance().dispose();
    }

    private static void companyCataloguePreConfig(Browser browser, Properties env) throws SequenceCompanyCatalogueException {
        CompanyCataloguePreConfig companyCataloguePreConfig = new CompanyCataloguePreConfig(
                browser,
                env.getProperty("NEW_APP_FTP_HOST"),
                env.getProperty("NEW_APP_FTP_PORT"),
                env.getProperty("NEW_APP_FTP_USER"),
                env.getProperty("NEW_APP_FTP_PASSWORD"),
                env.getProperty("NEW_APP_REMOTE_DIR_APP"),
                env.getProperty("NEW_APP_URL_DIR"),
                env.getProperty("NEW_APP_APP_EMAIL_ADMINISTRATOR"),
                env.getProperty("NEW_APP_APP_PASSWORD_ADMINISTRATOR"),
                env.getProperty("NEW_APP_PRETTY_APP_URL"),
                env.getProperty("NEW_APP_GOOGLE_DEV_LOGIN"),
                env.getProperty("NEW_APP_GOOGLE_DEV_PASSWORD"),
                env.getProperty("NEW_APP_GOOGLE_DEV_PROJECT_NAME"),
                env.getProperty("NEW_APP_APP_MAIN_TITLE"),
                env.getProperty("NEW_APP_ADSENSE_1_FILE"),
                env.getProperty("NEW_APP_ADSENSE_2_FILE"),
                env.getProperty("NEW_APP_ADSENSE_3_FILE"),
                env.getProperty("NEW_APP_ADSENSE_4_FILE"),
                env.getProperty("NEW_APP_ADSENSE_5_FILE")
        );
        companyCataloguePreConfig.launch();
    }

    private static void companyCatalogueBuildHome(Browser browser, Properties env) throws SequenceCompanyCatalogueException {
        CompanyCatalogueBuildHome companyCatalogueBuildHome = new CompanyCatalogueBuildHome(
                browser,
                env.getProperty("NEW_APP_FTP_HOST"),
                env.getProperty("NEW_APP_FTP_PORT"),
                env.getProperty("NEW_APP_FTP_USER"),
                env.getProperty("NEW_APP_FTP_PASSWORD"),
                env.getProperty("NEW_APP_REMOTE_DIR_APP"),
                env.getProperty("NEW_APP_URL_DIR"),
                env.getProperty("NEW_APP_SKELETON_APP_ZIP"),
                false,
                env.getProperty("NEW_APP_HOME_URL_PANEL"),
                env.getProperty("NEW_APP_HOME_LOGIN"),
                env.getProperty("NEW_APP_HOME_PASSWORD"),
                env.getProperty("NEW_APP_HOME_HOSTING_ID"),
                env.getProperty("NEW_APP_HOME_DATABASE_NAME_SUFFIX"),
                env.getProperty("NEW_APP_HOME_DATABASE_PASSWORD"),
                env.getProperty("NEW_APP_HOME_DATABASE_DESCRIPTION"),
                env.getProperty("NEW_APP_HOME_DATABASE_HOST"),
                env.getProperty("NEW_APP_HOME_DATABASE_PORT"),
                env.getProperty("NEW_APP_APP_EMAIL_ADMINISTRATOR"),
                env.getProperty("NEW_APP_RESOURCE_FILE_WP_CONFIG_TEMPLATE"),
                env.getProperty("NEW_APP_RESOURCE_FILE_IMPORT_SQL_TEMPLATE")
        );
        companyCatalogueBuildHome.launch();
    }
}
