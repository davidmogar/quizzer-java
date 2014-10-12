package com.davidmogar.quizzer.deserializers;

import org.junit.Test;

import java.net.URL;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestsDeserializerTest {

    @Test
    public void testDeserialize() throws Exception {
        String testsContent = "";
        URL testsUrl = getClass().getResource("/tests.json");

        try {
            testsContent = new String(readAllBytes(get(testsUrl.toURI())));
        } catch (Exception e) {
            fail("Unexpected exception" + e);
        }

        List<com.davidmogar.quizzer.domain.Test> tests = TestsDeserializer.deserialize(testsContent);

        assertNotNull("Tests is null", tests);
        assertTrue("Unexpected size for tests list", tests.size() == 1);
        assertTrue("Unexpected value for questions url of first test", tests.get(0).getQuestionsUrl().equals
                (getClass().getResource("/questions.json")));
        assertTrue("Unexpected value for grades url of second test", tests.get(0).getGradesUrl().equals
                (getClass().getResource("/grades.json")));
    }
}