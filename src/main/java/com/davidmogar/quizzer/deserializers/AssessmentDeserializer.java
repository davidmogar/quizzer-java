package com.davidmogar.quizzer.deserializers;

import com.davidmogar.quizzer.domain.Answer;
import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.questions.Question;
import com.google.gson.*;

import java.util.HashMap;
import java.util.List;

public class AssessmentDeserializer {

    @SuppressWarnings("unchecked")
    public static HashMap<Long, List<Answer>> deserializeAnswers(String json) throws JsonSyntaxException {
        return deserialize(json, new AnswersDeserializer());
    }

    @SuppressWarnings("unchecked")
    public static HashMap<Long, Grade> deserializeGrades(String json) throws JsonSyntaxException {
        return deserialize(json, new GradesDeserializer());
    }

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
