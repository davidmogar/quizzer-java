package com.davidmogar.quizzer.loaders;

import com.davidmogar.quizzer.Assessment;
import com.davidmogar.quizzer.deserializers.AssessmentDeserializer;
import com.davidmogar.quizzer.utils.UrlReader;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URL;

public class AssessmentLoader {

    /**
     * Returns a new Assessment created with the data obtained from the URLs received as arguments.
     *
     * @param questionsUrl URL to the questions file
     * @param answersUrl   URL to the answers file
     * @param gradesUrl    URL to the grades file
     * @return an Assessment with the data from the URLs received as arguments
     * @throws IOException if there is an error while fetching content from the given URLs
     */
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

    /**
     * Returns a new Assessment created with the data obtained from the JSONs received as arguments.
     *
     * @param questionsJson JSON representation of the questions file
     * @param answersJson   JSON representation of the answers file
     * @param gradesJson    JSON representation of the grades file
     * @return an Assessment with the data from the JSONs received as arguments
     */
    public static Assessment loadAssessment(String questionsJson, String answersJson,
                                            String gradesJson) throws IOException {
        if (questionsJson == null || answersJson == null) {
            throw new IllegalArgumentException("Questions and answers JSONs cannot be null");
        }

        return createAssessment(questionsJson, answersJson, gradesJson);
    }

    /**
     * Creates and returns an Assessment with the data obtained after deserialize the given JSONs.
     *
     * @param questionsJson JSON representation of the questions file
     * @param answersJson   JSON representation of the answers file
     * @param gradesJson    JSON representation of the grades file
     * @return an Assessment with the data from the JSONs received as arguments
     */
    private static Assessment createAssessment(String questionsJson, String answersJson, String gradesJson) {
        Assessment assessment = new Assessment();

        try {
            assessment.setQuestions(AssessmentDeserializer.deserializeQuestions(questionsJson));
            assessment.setAnswers(AssessmentDeserializer.deserializeAnswers(answersJson));

            if (gradesJson != null) {
                assessment.setGrades(AssessmentDeserializer.deserializeGrades(gradesJson));
            }
        } catch (JsonSyntaxException e) {

            /* Impossible to parse assessment. Return null */
            assessment = null;
        }

        return assessment;
    }

}
