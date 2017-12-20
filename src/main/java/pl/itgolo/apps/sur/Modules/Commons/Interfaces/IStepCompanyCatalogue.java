package pl.itgolo.apps.sur.Modules.Commons.Interfaces;

import pl.itgolo.apps.sur.Modules.Commons.Exceptions.StepCompanyCatalogueException;

/**
 * The interface Step company catalogue.
 */
public interface IStepCompanyCatalogue {

    /**
     * Run step.
     *
     * @throws StepCompanyCatalogueException the step company catalogue exception
     */
    void launchStep() throws StepCompanyCatalogueException;

}
