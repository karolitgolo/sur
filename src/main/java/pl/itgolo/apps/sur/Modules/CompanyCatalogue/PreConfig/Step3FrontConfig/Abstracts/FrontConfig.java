package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step3FrontConfig.Abstracts;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BooleanUtils;

/**
 * The type Smtp config.
 */
public abstract class FrontConfig implements IStepCompanyCatalogue{

    @Override
    public void launchStep() throws StepCompanyCatalogueException {
        LogService.debug("Launch before");
        before();
        LogService.debug("Launch set configuration");
        if (BooleanUtils.isNullOrFalse(setConfiguration())) {
            throw new StepCompanyCatalogueException("Failed set configuration in step 'Front config'");
        }
        LogService.debug("Launch validate set configuration");
        if (BooleanUtils.isNullOrFalse(validateSetConfiguration())) {
            throw new StepCompanyCatalogueException("Failed validate set configuration in step 'Front config'");
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
     * Sets configuration.
     *
     * @return the configuration
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean setConfiguration() throws StepCompanyCatalogueException;

    /**
     * Validate set configuration boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateSetConfiguration() throws StepCompanyCatalogueException;


    /**
     * After.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract void after() throws StepCompanyCatalogueException;

}