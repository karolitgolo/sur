package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step3ImportSql.Abstracts;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BooleanUtils;

/**
 * The type Import sql.
 */
public abstract class ImportSql implements IStepCompanyCatalogue{

    @Override
    public void launchStep() throws StepCompanyCatalogueException {
        LogService.debug("Launch before");
        before();
        LogService.debug("Launch create SQL file");
        if (BooleanUtils.isNullOrFalse(createSqlFile())) {
            throw new StepCompanyCatalogueException("Failed create SQL file in step 'Import SQL'");
        }
        LogService.debug("Launch remote import SQL");
        if (BooleanUtils.isNullOrFalse(remoteImportSql())) {
            throw new StepCompanyCatalogueException("Failed remote import SQL in step 'Import SQL'");
        }
        LogService.debug("Launch after");
        after();
    }

    /**
     * Before.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract void before() throws StepCompanyCatalogueException;

    /**
     * Create sql file boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean createSqlFile() throws StepCompanyCatalogueException;

    /**
     * Remote import sql boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean remoteImportSql() throws StepCompanyCatalogueException;


    /**
     * After.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract void after() throws StepCompanyCatalogueException;

}