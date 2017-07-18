package patryk.songapp;

import java.io.IOException;

/**
 * Interface to implement HTTP connection behavior between the library and
 * iTunes.
 *
 * @see URLConnector
 */
public interface Connector {

    /**
     * Perform an HTTP request. Return the response as {@link String}.
     *
     * @param link
     *            the link, as output from {@link Search#build()}
     * @return iTunes response as {@link String}, never {@code null}
     * @throws IOException
     *             if a problem occurred communicating with iTunes
     */
    public String get(String link) throws IOException;

}