package com.davidmogar.quizzer.loaders;

import com.davidmogar.quizzer.Assessment;
import com.davidmogar.quizzer.deserializers.AssessmentDeserializer;
import com.davidmogar.quizzer.utils.UrlReader;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URL;

public class AssessmentLoader {

    public static Assessment loadAssessment(URL questionsUrl, URL answersUrl, URL gradesUrl) throws IOException {
        if (questionsUrl == null || answersUrl == null) {
            throw new IllegalArgumentException("Questions and answers URLs cannot be null");
        }

        String questionsJson = UrlReader.getStreamAsString(questionsUrl);
        String answersJson = UrlReader.getStreamAsString(answersUrl);

        String gradesJson = null;
        if (gradesUrl != null) {
            gradesJson = UrlReader.getStreamAsString(gradesUrl);
        }

        return createAssessment(questionsJson, answersJson, gradesJson);
    }

    public static Assessment loadAssessment(String questionsJson, String answersJson,
            String gradesJson) throws IOException {
        if (questionsJson == null || answersJson == null) {
            throw new IllegalArgumentException("Questions and answers JSONs cannot be null");
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
