package com.davidmogar.quizzer;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static org.junit.Assert.*;

public class AssessmentLoaderTest {

    @Test
    public void testLoadAssessment() throws Exception {
        URL questions = getClass().getResource("/questions.json");
        URL answers = getClass().getResource("/answers.json");
        URL grades = getClass().getResource("/grades.json");

        assertNotNull("Missing questions file", questions);
        assertNotNull("Missing answers file", answers);
        assertNotNull("Missing grades file", grades);

        try {
            Assessment assessment = AssessmentLoader.loadAssessment(questions, answers, grades);
            assertNotNull("Assessment not loaded", assessment);

            assessment = AssessmentLoader.loadAssessment(questions, answers, null);
            assertNotNull("Assessment not loaded", assessment);
        } catch(IOException e) {
            fail("Exception not expected");
        }
    }
}