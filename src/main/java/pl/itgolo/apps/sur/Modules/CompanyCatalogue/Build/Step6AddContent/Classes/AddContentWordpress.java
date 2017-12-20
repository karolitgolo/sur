package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step6AddContent.Classes;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step6AddContent.Abstracts.AddContent;

/**
 * The type Import sql home.
 */
public class AddContentWordpress extends AddContent {

    private String databaseHost;
    private String databaseName;
    private String databaseUser;
    private String databasePassword;

    public AddContentWordpress(String databaseHost, String databaseName, String databaseUser, String databasePassword) {
        this.databaseHost = databaseHost;
        this.databaseName = databaseName;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
    }

    @Override
    protected void before() throws StepCompanyCatalogueException {

    }

    @Override
    protected Boolean addContent() throws StepCompanyCatalogueException {
        // add content by external app
        return true;
    }

    @Override
    protected Boolean validateAddContent() throws StepCompanyCatalogueException {
        // validate add content by external app
        return true;
    }

    @Override
    protected void after() throws StepCompanyCatalogueException {

    }
}
