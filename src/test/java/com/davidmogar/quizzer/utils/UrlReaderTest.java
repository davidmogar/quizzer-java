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
            e.printStackTrace();
        } catch (URISyntaxException e) {
            fail("URISyntaxException is not expected for the given URL");
        }
    }
}