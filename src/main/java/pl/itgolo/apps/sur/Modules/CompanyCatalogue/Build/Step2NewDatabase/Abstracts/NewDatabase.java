package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step2NewDatabase.Abstracts;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BooleanUtils;

/**
 * The type New database.
 */
public abstract class NewDatabase implements IStepCompanyCatalogue{

    @Override
    public void launchStep() throws StepCompanyCatalogueException {
        LogService.debug("Launch before");
        before();
        LogService.debug("Launch authorization to panel");
        if (BooleanUtils.isNullOrFalse(authorizationPanel())) {
            throw new StepCompanyCatalogueException("Failed authorization to panel in step 'New database'");
        }
        LogService.debug("Launch validate no exist database");
        if (BooleanUtils.isNullOrFalse(validateNoExistDatabase())) {
            throw new StepCompanyCatalogueException("Failed validate no exist database in step 'New database'");
        }
        LogService.debug("Launch create database");
        if (BooleanUtils.isNullOrFalse(createDatabase())) {
            throw new StepCompanyCatalogueException("Failed create database in step 'New database'");
        }
        LogService.debug("Launch validate created database");
        if (BooleanUtils.isNullOrFalse(validateCreatedDatabase())) {
            throw new StepCompanyCatalogueException("Failed validate created database in step 'New database'");
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
     * Authorization panel boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean authorizationPanel() throws StepCompanyCatalogueException;

    /**
     * Validate no exist database boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateNoExistDatabase() throws StepCompanyCatalogueException;

    /**
     * Create database boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean createDatabase() throws StepCompanyCatalogueException;

    /**
     * Validate created database boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateCreatedDatabase() throws StepCompanyCatalogueException;

    /**
     * After.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract void after() throws StepCompanyCatalogueException;

}