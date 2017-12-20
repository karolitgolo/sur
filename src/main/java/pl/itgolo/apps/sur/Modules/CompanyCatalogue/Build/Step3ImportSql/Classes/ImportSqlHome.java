package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step3ImportSql.Classes;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Utils.FileUtils;
import pl.itgolo.apps.sur.Modules.Commons.Utils.JdbiUtils;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step3ImportSql.Abstracts.ImportSql;
import pl.itgolo.libs.chromium.Browser;

import java.io.File;

/**
 * The type Import sql home.
 */
public class ImportSqlHome extends ImportSql {
    private String login;
    private String hostingId;
    private String databaseNameSuffix;
    private String databasePassword;
    private String appUrl;
    private String databaseHost;
    private Integer databasePort;
    private String appEmailAdministrator;
    private File importSqlTemplateFile;
    private String databaseNameFull;
    private Jdbi jdbi;
    private File importSqlFile;

    /**
     * Instantiates a new Import sql home.
     *
     * @param browser               the browser
     * @param login                 the login
     * @param hostingId             the hosting id
     * @param databaseNameSuffix    the database name suffix
     * @param databasePassword      the database password
     * @param appUrl                the app url
     * @param databaseHost          the database host
     * @param databasePort          the database port
     * @param appEmailAdministrator the app email administrator
     */
    public ImportSqlHome(String login, String hostingId, String databaseNameSuffix, String databasePassword, String appUrl, String databaseHost, Integer databasePort, String appEmailAdministrator, File importSqlTemplateFile) {
        this.login = login;
        this.hostingId = hostingId;
        this.databaseNameSuffix = databaseNameSuffix;
        this.databasePassword = databasePassword;
        this.appUrl = appUrl;
        this.databaseHost = databaseHost;
        this.databasePort = databasePort;
        this.appEmailAdministrator = appEmailAdministrator;
        this.importSqlTemplateFile = importSqlTemplateFile;
        this.databaseNameFull = this.hostingId + "_" + this.databaseNameSuffix;
        this.jdbi = Jdbi.create(String.format("jdbc:mysql://%1$s:%2$s/%3$s", this.databaseHost, this.databasePort, this.databaseNameFull),
                this.databaseNameFull, this.databasePassword);
    }

    @Override
    protected void before() throws StepCompanyCatalogueException {

    }

    @Override
    protected Boolean createSqlFile() throws StepCompanyCatalogueException {
        try {
            String importSqlTemplate = FileUtils.readFromFile(this.importSqlTemplateFile);
            importSqlTemplate = importSqlTemplate.replace("{{appUrl}}", this.appUrl);
            importSqlTemplate = importSqlTemplate.replace("{{emailAdministrator}}", this.appEmailAdministrator);
            this.importSqlFile = File.createTempFile("importSql", ".sql");
            FileUtils.write(importSqlTemplate, this.importSqlFile);
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected Boolean remoteImportSql() throws StepCompanyCatalogueException {
        try {
            Handle handle = JdbiUtils.createHandleUtf8mb4(this.jdbi);

            handle.createScript(FileUtils.readFromFile(this.importSqlFile)).execute();
            handle.close();
            return true;
        } catch (Exception e) {
            throw new StepCompanyCatalogueException(e);
        }
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {

    }

}
