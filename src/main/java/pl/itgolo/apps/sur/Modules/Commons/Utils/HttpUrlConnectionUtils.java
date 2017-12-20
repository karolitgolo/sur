package pl.itgolo.apps.sur.Modules.Commons.Utils;

import pl.itgolo.apps.sur.Modules.Commons.Logger.Services.LogService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The type Http url connection utils.
 */
public class HttpUrlConnectionUtils {

    /**
     * Can connect http url connection.
     *
     * @param url         the url
     * @param timeoutRead the timeout read
     * @param attempts    the attempts
     * @return the http url connection
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static HttpURLConnection canConnect(URL url, Integer timeoutRead, Integer attempts) throws IOException, InterruptedException {
        for (Integer i = 0; i <= attempts; i++) {
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(timeoutRead * 1000);
                conn.setRequestMethod("HEAD");
                conn.getInputStream();
                return conn;
            } catch (Exception e) {
                if (i.equals(attempts)) {
                    throw new IOException(e);
                }
                LogService.debug(String.format("Wait %1$s of 120 seconds for response from %2$s", i + 2, url.toString()));
                Thread.sleep(1000);
            }
        }
        throw new IOException("Can not connect to http url: " + url.toString());
    }

    /**
     * Gets content body.
     *
     * @param url         the url
     * @param timeoutRead the timeout read
     * @param attempts    the attempts
     * @return the content body
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static String getContentBody(URL url, Integer timeoutRead, Integer attempts) throws IOException, InterruptedException {
        for (Integer i = 0; i <= attempts; i++) {
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(timeoutRead * 1000);
                conn.setRequestMethod("GET");
                StringBuffer sb = new StringBuffer();
                InputStream is = new BufferedInputStream(conn.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String inputLine = "";
                    while ((inputLine = br.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    return sb.toString();
            } catch (Exception e) {
                if (i.equals(attempts)) {
                    throw new IOException(e);
                }
                LogService.debug(String.format("Wait %1$s of 120 seconds for response from %2$s", i + 2, url.toString()));
                Thread.sleep(1000);
            }
        }
        throw new IOException("Can not connect to http url: " + url.toString());
    }
}
