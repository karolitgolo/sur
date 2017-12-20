package pl.itgolo.apps.sur.Modules.Commons.Utils;

/**
 * The type Boolean utils.
 */
public class BooleanUtils {

    /**
     * Is null or false boolean.
     *
     * @param variable the variable
     * @return the boolean
     */
    public static Boolean isNullOrFalse(Boolean variable){
        if (variable == null){
            return true;
        }
        return !variable;
    }
}
