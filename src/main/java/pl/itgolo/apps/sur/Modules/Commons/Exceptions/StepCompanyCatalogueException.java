package pl.itgolo.apps.sur.Modules.Commons.Exceptions;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:27
 * Project name: crawler
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public class StepCompanyCatalogueException extends Exception {

    /**
     * Constructor
     */
    public StepCompanyCatalogueException() {

    }

    /**
     * Constructor
     * @param message message exception
     */
    public StepCompanyCatalogueException(String message) {
        super (message);
    }


    /**
     * Constructor
     * @param cause cause exception
     */
    public StepCompanyCatalogueException(Throwable cause) {
        super (cause);
    }

    /**
     * Constructor
     * @param message message exception
     * @param cause cause exception
     */
    public StepCompanyCatalogueException(String message, Throwable cause) {
        super (message, cause);
    }
}
