package com.davidmogar.quizzer;

import com.davidmogar.quizzer.deserializers.AssessmentDeserializer;
import com.davidmogar.quizzer.utils.UrlReader;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URL;

public class AssessmentLoader {

    public static Assessment loadAssessment(URL questionsUri, URL answersUri, URL gradesUri) throws IOException {
        if (questionsUri == null || answersUri == null) {
            throw new IllegalArgumentException("Questions and answers URIs cannot be null");
        }

        String questionsJson = UrlReader.getStreamAsString(questionsUri);
        String answersJson = UrlReader.getStreamAsString(answersUri);

        String gradesJson = null;
        if (gradesUri != null) {
            gradesJson = UrlReader.getStreamAsString(gradesUri);
        }

        return createAssessment(questionsJson, answersJson, gradesJson);
    }

    private static Assessment createAssessment(String questionsJson, String answersJson, String gradesJson) {
        Assessment assessment = new Assessment();

        try {
            assessment.setQuestions(AssessmentDeserializer.deserializeQuestions(questionsJson));
            assessment.setAnswers(AssessmentDeserializer.deserializeAnswers(answersJson));

            if (gradesJson != null) {
                assessment.setGrades(AssessmentDeserializer.deserializeGrades(gradesJson));
            }
        } catch(JsonSyntaxException e) {

            /* Impossible to parse assessment. Return null */
            assessment = null;
        }

        return assessment;
    }
}
