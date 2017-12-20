package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step6AddContent.Abstracts;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BooleanUtils;

/**
 * The type Add content.
 */
public abstract class AddContent implements IStepCompanyCatalogue{

    @Override
    public void launchStep() throws StepCompanyCatalogueException {
        LogService.debug("Launch before");
        before();
        LogService.debug("Launch add content");
        if (BooleanUtils.isNullOrFalse(addContent())) {
            throw new StepCompanyCatalogueException("Failed add content in step 'Add content'");
        }
        LogService.debug("Launch validate add content");
        if (BooleanUtils.isNullOrFalse(validateAddContent())) {
            throw new StepCompanyCatalogueException("Failed validate add content in step 'Add content'");
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
     * Add content boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean addContent() throws StepCompanyCatalogueException;

    /**
     * Validate add content boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateAddContent() throws StepCompanyCatalogueException;

    /**
     * After.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract void after() throws StepCompanyCatalogueException;

}