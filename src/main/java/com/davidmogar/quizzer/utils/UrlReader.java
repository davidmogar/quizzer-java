package com.davidmogar.quizzer.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;

public class UrlReader {

    /**
     * Gets the content of the URL as an String.
     * @param uri URL to fetch data from
     * @return String with the URL contents
     * @throws IOException if URL was invalid or the connection was refused
     */
    public static String getStreamAsString(URI uri) throws IOException {
        URLConnection connection = uri.toURL().openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }

        bufferedReader.close();

        return response.toString();
    }
}
