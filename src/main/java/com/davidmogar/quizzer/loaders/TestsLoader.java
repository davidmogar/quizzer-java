package com.davidmogar.quizzer.loaders;

import com.davidmogar.quizzer.deserializers.TestsDeserializer;
import com.davidmogar.quizzer.domain.Test;
import com.davidmogar.quizzer.utils.UrlReader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TestsLoader {

    /**
     * Returns a list of tests objects loaded from the file referenced by the URL argument.
     *
     * @param testsUrl URL to the tests file
     * @return a list of tests objects
     * @throws IOException if there is an error while fetching content from the given URL
     */
    public static List<Test> loadTests(URL testsUrl) throws IOException {
        if (testsUrl == null) {
            throw new IllegalArgumentException("Tests URL cannot be null");
        }

        String testsJson = UrlReader.getStreamAsString(testsUrl);

        return TestsDeserializer.deserialize(testsJson);
    }

}
