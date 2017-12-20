package pl.itgolo.apps.sur.Modules.CompanyCatalogue.Build.Step1UnzipSkeleton.Abstracts;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;
import pl.itgolo.apps.sur.Modules.Commons.Interfaces.IStepCompanyCatalogue;
import pl.itgolo.apps.sur.Modules.Commons.Utils.BooleanUtils;
import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;

/**
 * The type Unzip skeleton.
 */
public abstract class UnzipSkeleton implements IStepCompanyCatalogue {

    @Override
    public void launchStep() throws StepCompanyCatalogueException {
        LogService.debug("Launch before");
        before();
        LogService.debug("Launch validate zip file");
        if (BooleanUtils.isNullOrFalse(validateZipFile())) {
            throw new StepCompanyCatalogueException("Failed validate zip file in step 'Unzip skeleton'");
        }
        LogService.debug("Launch validate file transporter");
        if (BooleanUtils.isNullOrFalse(validateFileTransporter())) {
            throw new StepCompanyCatalogueException("Failed validate file transporter in step 'Unzip skeleton'");
        }
        LogService.debug("Launch upload skeleton");
        if (BooleanUtils.isNullOrFalse(uploadSkeleton())) {
            throw new StepCompanyCatalogueException("Failed upload skeleton in step 'Unzip skeleton'");
        }
        LogService.debug("Launch validate updated skeleton");
        if (BooleanUtils.isNullOrFalse(validateUploadedSkeleton())) {
            throw new StepCompanyCatalogueException("Failed validate upload skeleton in step 'Unzip skeleton'");
        }
        LogService.debug("Launch remote unzip skeleton");
        if (BooleanUtils.isNullOrFalse(remoteUnzipSkeleton())) {
            throw new StepCompanyCatalogueException("Failed remote unzip skeleton in step 'Unzip skeleton'");
        }
        LogService.debug("Launch validate remote unzip skeleton");
        if (BooleanUtils.isNullOrFalse(validateRemoteUnzipSkeleton())) {
            throw new StepCompanyCatalogueException("Failed validate remote unzip skeleton in step 'Unzip skeleton'");
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
     * Validate zip file boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateZipFile() throws StepCompanyCatalogueException;

    /**
     * Validate file transporter boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateFileTransporter() throws StepCompanyCatalogueException;

    /**
     * Upload skeleton boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean uploadSkeleton() throws StepCompanyCatalogueException;

    /**
     * Validate uploaded skeleton boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateUploadedSkeleton() throws StepCompanyCatalogueException;

    /**
     * Remote unzip skeleton boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean remoteUnzipSkeleton() throws StepCompanyCatalogueException;

    /**
     * Validate remote unzip skeleton boolean.
     *
     * @return the boolean
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract Boolean validateRemoteUnzipSkeleton() throws StepCompanyCatalogueException;

    /**
     * After.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    protected abstract void after() throws StepCompanyCatalogueException;
}
