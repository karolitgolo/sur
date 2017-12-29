package pl.itgolo.apps.sur.Modules.Commons.Abstracts;

import org.cef.CefApp;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Classes.LogOutConsole;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Interfaces.ILogOut;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IActionSites;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;
import pl.itgolo.libs.chromium.Scenes.ConfigurationBrowser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * The type Action sites.
 */
public abstract class ActionSites implements IActionSites {
    /**
     * The Browser.
     */
    protected Browser browser;

    /**
     * The Env.
     */
    protected Properties env;

    /**
     * The Sites.
     */
    protected  List<String> sites;

    private void initialize() throws ChromiumException, IOException {
        LogService.logOuts.addAll(Arrays.asList((ILogOut) new LogOutConsole()));
        ConfigurationBrowser config = new ConfigurationBrowser();
        config.setEnableGeolocation(false);
        config.setEnableJSDialog(false);
        this.browser = new Browser(config);
        this.env = new Properties();
        this.env.load(new InputStreamReader(new FileInputStream(new File("env.properties")), Charset.forName("UTF-8")));
        this.sites = FileUtils.toLines(new File(env.getProperty("COMPANYCATALOGUE_SITES_FILE")));
    }

    /**
     * Action.
     *
     * @throws Exception the exception
     */
    protected abstract void action() throws Exception;

    @Override
    public void launch() throws Exception {
        this.initialize();
        this.action();
        this.exit();
    }

    private void exit(){
        this.browser.getJFrame().cefApp.shutdownFix();
        CefApp.getInstance().dispose();
    }
}