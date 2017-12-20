package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step5ConfigFile.Abstracts;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BooleanUtils;

/**
 * The type Config file.
 */
public abstract class ConfigFile implements IStepCompanyCatalogue{

    @Override
    public void launchStep() throws StepCompanyCatalogueException {
        LogService.debug("Launch before");
        before();
        LogService.debug("Launch send file");
        if (BooleanUtils.isNullOrFalse(sendFile())) {
            throw new StepCompanyCatalogueException("Failed send file in step 'Config file'");
        }
        LogService.debug("Launch validate sent file");
        if (BooleanUtils.isNullOrFalse(validateSentFile())) {
            throw new StepCompanyCatalogueException("Failed validate sent file in step 'Config file'");
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
     * Send file boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean sendFile() throws StepCompanyCatalogueException;

    /**
     * Validate sent file boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateSentFile() throws StepCompanyCatalogueException;

    /**
     * After.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract void after() throws StepCompanyCatalogueException;

}