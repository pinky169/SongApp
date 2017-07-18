package patryk.songapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

/**
 * Default {@link Connector} implementation using {@link URLConnection}.
 */
public class URLConnector implements Connector, Serializable {

    private static final long serialVersionUID = 1476515538667L;

    /**
     * Reusable, threadsafe {@link URLConnector} instance.
     */
    public static final URLConnector INSTANCE = new URLConnector();

    /**
     * {@inheritDoc}
     */
    public String get(String link) throws IOException {
        URL url = new URL(link);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
            sb.append(System.getProperty("line.separator"));
        }
        in.close();
        return sb.toString().trim();
    }

}
