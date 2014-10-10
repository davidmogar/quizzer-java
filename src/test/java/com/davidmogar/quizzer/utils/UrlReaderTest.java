package com.davidmogar.quizzer.utils;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.*;

public class UrlReaderTest {

    @Test
    public void testGetStreamAsString() throws Exception {
        try {
            String result = UrlReader.getStreamAsString(new URL("http://google.com"));
            assertNotNull(result);
            assertNotEquals(result, "");
        } catch (IOException e) {
            fail("IOException was not expected for the given URL" + e);
        }
    }
}