package com.davidmogar.quizzer.loaders;

import com.davidmogar.quizzer.deserializers.TestsDeserializer;
import com.davidmogar.quizzer.domain.Test;
import com.davidmogar.quizzer.utils.UrlReader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TestsLoader {

    public static List<Test> loadTests(URL testsUrl) throws IOException {
        if (testsUrl == null) {
            throw new IllegalArgumentException("Tests URL cannot be null");
        }

        String testsJson = UrlReader.getStreamAsString(testsUrl);
        return TestsDeserializer.deserialize(testsJson);
    }

}
