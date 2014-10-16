package com.davidmogar.quizzer.deserializers;

import com.davidmogar.quizzer.domain.Answer;
import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.questions.Question;
import com.google.gson.*;

import java.util.HashMap;
import java.util.List;

public class AssessmentDeserializer {

    /**
     * Deserializes the JSON representation received as arguments to a map of student ids to Answer objects.
     *
     * @param json JSON representation of the answers objects
     * @return a map of student ids to Answer objects
     * @throws JsonParseException if the json is invalid
     */
    @SuppressWarnings("unchecked")
    public static HashMap<Long, List<Answer>> deserializeAnswers(String json) throws JsonSyntaxException {
        return deserialize(json, new AnswersDeserializer());
    }

    /**
     * Deserializes the JSON representation received as arguments to a map of grades ids to Grade objects.
     *
     * @param json JSON representation of the grades objects
     * @return a map of grades ids to Grade objects
     * @throws JsonParseException if the json is invalid
     */
    @SuppressWarnings("unchecked")
    public static HashMap<Long, Grade> deserializeGrades(String json) throws JsonSyntaxException {
        return deserialize(json, new GradesDeserializer());
    }

    /**
     * Deserializes the JSON representation received as arguments to a map of questions ids to Question objects.
     *
     * @param json JSON representation of the questions objects
     * @return a map of questions ids to Question objects
     * @throws JsonParseException if the json is invalid
     */
    @SuppressWarnings("unchecked")
    public static HashMap<Long, Question> deserializeQuestions(String json) throws JsonSyntaxException {
        return deserialize(json, new QuestionsDeserializer());
    }

    private static HashMap deserialize(String json, JsonDeserializer deserializer) {
        JsonParser parser = new JsonParser();
        JsonObject rootObject = parser.parse(json).getAsJsonObject();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(HashMap.class, deserializer);
        Gson gson = gsonBuilder.create();

        return gson.fromJson(rootObject, HashMap.class);
    }

}
