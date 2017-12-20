package pl.itgolo.apps.sur.Modules.CompanyCatalogue.PreConfig.Step1InsertAsset.Abstracts;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BooleanUtils;

/**
 * The type Insert asset.
 */
public abstract class InsertAsset implements IStepCompanyCatalogue{

    @Override
    public void launchStep() throws StepCompanyCatalogueException {
        LogService.debug("Launch before");
        before();
        LogService.debug("Launch send assets");
        if (BooleanUtils.isNullOrFalse(sendAssets())) {
            throw new StepCompanyCatalogueException("Failed send assets in step 'Insert asset'");
        }
        LogService.debug("Launch validate sent assets");
        if (BooleanUtils.isNullOrFalse(validateSentAssets())) {
            throw new StepCompanyCatalogueException("Failed validate sent assets in step 'Insert asset'");
        }
        LogService.debug("Launch clear cache assets");
        if (BooleanUtils.isNullOrFalse(clearCacheAssets())) {
            throw new StepCompanyCatalogueException("Failed clear cache assets in step 'Insert asset'");
        }
        LogService.debug("Launch validate clear cache assets");
        if (BooleanUtils.isNullOrFalse(validateClearCacheAssets())) {
            throw new StepCompanyCatalogueException("Failed validate clear cache assets in step 'Insert asset'");
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
     * Send assets boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean sendAssets() throws StepCompanyCatalogueException;

    /**
     * Validate sent assets boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateSentAssets() throws StepCompanyCatalogueException;

    protected abstract Boolean clearCacheAssets() throws StepCompanyCatalogueException;

    protected abstract Boolean validateClearCacheAssets() throws StepCompanyCatalogueException;

    /**
     * After.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract void after() throws StepCompanyCatalogueException;

}