package com.davidmogar.quizzer.loaders;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

public class TestsLoaderTest {

    @Test
    public void testLoadTests() throws Exception {
        URL testsUrl = getClass().getResource("/tests.json");

        assertNotNull("Missing tests file", testsUrl);

        try {
            List<com.davidmogar.quizzer.domain.Test> tests = TestsLoader.loadTests(testsUrl);
            assertNotNull("Assessment not loaded", tests);
            assertTrue("Unexpected tests list size", tests.size() == 1);
        } catch(IOException e) {
            fail("Exception not expected");
        }
    }
}