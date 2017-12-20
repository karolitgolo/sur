package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step2NewDatabase.Classes;

import org.jsoup.nodes.Element;
import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BrowserUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.HomeUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step2NewDatabase.Abstracts.NewDatabase;
import pl.itgolo.libs.chromium.Browser;
import pl.itgolo.libs.chromium.Exceptions.ChromiumException;

/**
 * The type New database home.
 */
public class NewDatabaseHome extends NewDatabase {

    private Browser browser;
    private String urlPanel;
    private String loginPanel;
    private String passwordPanel;
    private String hostingId;
    private String databaseNameSuffix;
    private String databasePassword;
    private String databaseDescription;
    private String databaseNameFull;
    private Boolean deleteDatabaseIfExist;


    /**
     * Instantiates a new New database home.
     *
     * @param browser               the browser
     * @param urlPanel              the url panel
     * @param loginPanel            the login panel
     * @param passwordPanel         the password panel
     * @param hostingId             the hosting id
     * @param databaseNameSuffix    the database name suffix
     * @param databasePassword      the database password
     * @param databaseDescription   the database description
     * @param deleteDatabaseIfExist the delete database if exist
     */
    public NewDatabaseHome(Browser browser, String urlPanel, String loginPanel, String passwordPanel, String hostingId, String databaseNameSuffix, String databasePassword, String databaseDescription, Boolean deleteDatabaseIfExist) {
        this.deleteDatabaseIfExist = false;
        this.browser = browser;
        this.urlPanel = urlPanel;
        this.loginPanel = loginPanel;
        this.passwordPanel = passwordPanel;
        this.hostingId = hostingId;
        this.databaseNameSuffix = databaseNameSuffix;
        this.databasePassword = databasePassword;
        this.databaseDescription = databaseDescription;
        this.deleteDatabaseIfExist = deleteDatabaseIfExist;
        this.databaseNameFull = this.hostingId + "_" + this.databaseNameSuffix;
    }

    @Override
    protected void before() throws StepCompanyCatalogueException {

    }

    @Override
    protected Boolean authorizationPanel() throws StepCompanyCatalogueException {
        try {
            HomeUtils.authorization(this.urlPanel, this.loginPanel, this.passwordPanel, this.browser);
            return true;
        } catch (ChromiumException e) {
           throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateNoExistDatabase() throws StepCompanyCatalogueException {
        if (this.databaseNameSuffix.length() > 7) {
            throw new StepCompanyCatalogueException("Max length database surfix is 7");
        }
        try {
            this.browser.actions.navigateTo("https://panel.home.pl/services/servers/databases/id/" + this.hostingId);
            if (BrowserUtils.existElement(String.format("#mainList > tbody > tr > td:contains(%1$s_%2$s)", this.hostingId, this.databaseNameSuffix), this.browser)) {
                if (this.deleteDatabaseIfExist) {
                    Element tr =  this.browser.actions.getElement(String.format("#mainList > tbody > tr:has(td:contains(%1$s))", this.databaseNameFull));
                    Element checkBox = tr.select("input[type=checkbox]").first();
                    String idDatabase = checkBox.attr("value");
                    this.browser.actions.selectCheckBox(String.format("input[value='%1$s']", idDatabase), true);
                    this.browser.actions.click("#delete");
                    this.browser.actions.waitElement("#dialog_message_confirm_btn1", 15);
                    this.browser.actions.clickReload("#dialog_message_confirm_btn1", 60);
                } else {
                    throw new StepCompanyCatalogueException(String.format("Exist database %1$s_%2$s in hosting ID: %3$s", this.hostingId, this.databaseNameSuffix, this.hostingId));
                }
            }
            return true;
        } catch (ChromiumException e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean createDatabase() throws StepCompanyCatalogueException {
        try {
            this.browser.actions.navigateTo("https://panel.home.pl/services/servers/databases/add/id/" + this.hostingId);
            this.browser.actions.fillInput("input[name='db_name_suffix']", this.databaseNameSuffix);
            this.browser.actions.fillInput("input[name='password']", this.databasePassword);
            this.browser.actions.fillInput("input[name='password2']", this.databasePassword);
            this.browser.actions.fillInput("input[name='descr']", this.databaseDescription);
            this.browser.actions.clickReload("input[name='form_save']", 60);
        return true;
        } catch (ChromiumException e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean validateCreatedDatabase() throws StepCompanyCatalogueException {
        try {
            this.browser.actions.navigateTo("https://panel.home.pl/services/servers/databases/id/" + this.hostingId);
            return BrowserUtils.existElement(String.format("#mainList > tbody > tr > td:contains(%1$s_%2$s)", this.hostingId, this.databaseNameSuffix), this.browser);
        } catch (ChromiumException e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {

    }
}
