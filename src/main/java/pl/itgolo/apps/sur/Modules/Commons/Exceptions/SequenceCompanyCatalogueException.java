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
public class SequenceCompanyCatalogueException extends Exception {

    /**
     * Constructor
     */
    public SequenceCompanyCatalogueException() {

    }

    /**
     * Constructor
     * @param message message exception
     */
    public SequenceCompanyCatalogueException(String message) {
        super (message);
    }


    /**
     * Constructor
     * @param cause cause exception
     */
    public SequenceCompanyCatalogueException(Throwable cause) {
        super (cause);
    }

    /**
     * Constructor
     * @param message message exception
     * @param cause cause exception
     */
    public SequenceCompanyCatalogueException(String message, Throwable cause) {
        super (message, cause);
    }
}
