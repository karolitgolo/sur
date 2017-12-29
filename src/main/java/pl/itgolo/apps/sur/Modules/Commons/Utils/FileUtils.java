package pl.itgolo.apps.sur.Modules.Commons.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type File utils.
 */
public class FileUtils {

    /**
     * Write.
     *
     * @param content the content
     * @param file    the file
     * @throws IOException the io exception
     */
    public static void write(String content, File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(Paths.get(file.getCanonicalPath()), content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Read from resource string.
     *
     * @param resourcePath the resource path
     * @return the string
     * @throws URISyntaxException the uri syntax exception
     * @throws IOException        the io exception
     */
    public static String readFromResource(String resourcePath) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(FileUtils.class.getClass().getResource(resourcePath).toURI())));
    }

    /**
     * Read from file string.
     *
     * @param file the file
     * @return the string
     * @throws IOException the io exception
     */
    public static String readFromFile(File file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file.getCanonicalPath())));
    }

    /**
     * Resource to file file.
     *
     * @param resource the resource
     * @param file     the file
     * @return the file
     * @throws IOException the io exception
     */
    public static File resourceToFile(String resource, File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        InputStream is = FileUtils.class.getResourceAsStream(resource);
        FileOutputStream fos = new FileOutputStream(file);
        int byteCount = 0;
        byte[] bytes = new byte[1024];
        while ((byteCount = is.read(bytes)) != -1) {
            fos.write(bytes, 0, byteCount);
        }
        return file;
    }

    /**
     * To lines list.
     *
     * @param file the file
     * @return the list
     * @throws IOException the io exception
     */
    public static List<String> toLines(File file) throws IOException {
        String content =  new String(Files.readAllBytes(file.toPath()));
        List<String> lines = new ArrayList<>();
        lines.addAll(Arrays.asList(content.split(System.lineSeparator())));
        return lines;
    }
}
