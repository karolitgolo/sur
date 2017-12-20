package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step4InsertDomain.Abstracts;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BooleanUtils;

/**
 * The type Insert domain.
 */
public abstract class InsertDomain implements IStepCompanyCatalogue{

    @Override
    public void launchStep() throws StepCompanyCatalogueException {
        LogService.debug("Launch before");
        before();
        LogService.debug("Launch authorization panel");
        if (BooleanUtils.isNullOrFalse(authorizationPanel())) {
            throw new StepCompanyCatalogueException("Failed create SQL file in step 'Insert domain'");
        }
        LogService.debug("Launch insert domain");
        if (BooleanUtils.isNullOrFalse(insertDomain())) {
            throw new StepCompanyCatalogueException("Failed insert domain in step 'Insert domain'");
        }
        LogService.debug("Launch validate inserted domain");
        if (BooleanUtils.isNullOrFalse(validateInsertedDomain())) {
            throw new StepCompanyCatalogueException("Failed validate inserted domain in step 'Insert domain'");
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
     * Insert domain boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean insertDomain() throws StepCompanyCatalogueException;

    /**
     * Validate inserted domain boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateInsertedDomain() throws StepCompanyCatalogueException;


    /**
     * After.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract void after() throws StepCompanyCatalogueException;

}