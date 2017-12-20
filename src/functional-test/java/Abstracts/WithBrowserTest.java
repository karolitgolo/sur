package Abstracts;

import org.junit.rules.TemporaryFolder;
import pl.itgolo.libs.chromium.Browser;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * The type With browser test.
 */
public class WithBrowserTest {

    /**
     * The Props.
     */
    protected Properties props;

    /**
     * The Env.
     */
    protected Properties env;

    protected Browser browser;

   protected TemporaryFolder temporaryFolder;

    /**
     * Instantiates a new With browser test.
     *
     * @throws Exception the exception
     */
    public WithBrowserTest(Browser browser, TemporaryFolder temporaryFolder) throws Exception {
        this.browser = browser;
        this.temporaryFolder = temporaryFolder;
        this.props = new Properties();
        this.props.load(new FileInputStream("gradle.properties"));
        if (Files.exists(Paths.get("env.properties"))) {
            this.env = new Properties();
            this.env.load(new FileInputStream("env.properties"));
        }
    }
}
