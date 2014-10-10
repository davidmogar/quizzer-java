package com.davidmogar.quizzer.loaders;

import com.davidmogar.quizzer.Assessment;
import com.davidmogar.quizzer.loaders.AssessmentLoader;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class AssessmentLoaderTest {

    @Test
    public void testLoadAssessment() throws Exception {
        URL questionsUrl = getClass().getResource("/questions.json");
        URL answersUrl = getClass().getResource("/answers.json");
        URL gradesUrl = getClass().getResource("/grades.json");

        assertNotNull("Missing questions file", questionsUrl);
        assertNotNull("Missing answers file", answersUrl);
        assertNotNull("Missing grades file", gradesUrl);

        try {
            Assessment assessment = AssessmentLoader.loadAssessment(questionsUrl, answersUrl, gradesUrl);
            assertNotNull("Assessment not loaded", assessment);

            assessment = AssessmentLoader.loadAssessment(questionsUrl, answersUrl, null);
            assertNotNull("Assessment not loaded", assessment);
        } catch(IOException e) {
            fail("Exception not expected");
        }
    }
}