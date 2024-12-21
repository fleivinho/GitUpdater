package com.github.fleivinho.gitupdater.shared;

import com.google.gson.Gson;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author fleivius
 * @project https://github.com/fleivinho/GitUpdater
 */
public class WebAccessAPI {

    @SuppressWarnings("unchecked")
    public static <T> T getResultAsJson(String url, int timeout, Class<T> type) {
        try {
            URLConnection connection = new URL(url).openConnection();
            if (timeout >= 1) {
                connection.setConnectTimeout(timeout * 1000);
            }

            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return new Gson().fromJson(reader, type);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean downloadFile(String url, File dest) {
        try {
            URL file = new URL(url);
            InputStream is = file.openStream();
            dest.getParentFile().mkdirs();
            OutputStream os = new FileOutputStream(dest);
            byte[] data = new byte[1024];
            int count;
            while ((count = is.read(data, 0, 1024)) != -1) {
                os.write(data, 0, count);
            }
            os.flush();
            is.close();
            os.close();
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }
}
