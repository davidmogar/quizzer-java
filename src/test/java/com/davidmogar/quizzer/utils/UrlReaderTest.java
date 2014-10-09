package com.davidmogar.quizzer.utils;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class UrlReaderTest {

    @Test
    public void testGetStreamAsString() throws Exception {
        try {
            String result = UrlReader.getStreamAsString(new URI("http://google.com"));
            assertNotNull(result);
            assertNotEquals(result, "");
        } catch (IOException e) {
            fail("IOException was not expected for the given URL");
        } catch (URISyntaxException e) {
            fail("URISyntaxException was not expected for the given URL");
        }
    }
}