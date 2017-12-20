package pl.itgolo.apps.sur.Modules.Commons.Utils;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

/**
 * The type Jdbi utils.
 */
public class JdbiUtils {
    /**
     * Character set handle.
     *
     * @param handle the handle
     * @param coding the coding
     */
    public static void characterSetHandle(Handle handle, String coding) {
        handle.execute("SET NAMES "+coding+";");
        handle.execute("SET CHARACTER SET "+coding+";");
    }

    /**
     * Create handle utf 8 mb 4 handle.
     *
     * @param jdbi the jdbi
     * @return the handle
     */
    public static Handle createHandleUtf8mb4(Jdbi jdbi) {
        Handle handle = jdbi.open();
        JdbiUtils.characterSetHandle(handle, "utf8mb4");
        return handle;
    }
}
