package pl.itgolo.apps.sur.Modules.Commons.Utils;

import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class ZipUtils {

    /**
     * File is valid boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static Boolean isValid(final File file) {
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(file);
            return true;
        } catch (Exception e) {
            LogService.warn(e.getMessage());
            return false;
        } finally {
            try {
                if (zipfile != null) {
                    zipfile.close();
                    zipfile = null;
                }
            } catch (IOException e) {
            }
        }
    }
}
