package com.davidmogar.quizzer.deserializers;

import com.davidmogar.quizzer.domain.Answer;
import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.questions.Question;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public class AssessmentDeserializer {

    public static HashMap<Long, List<Answer>> deserializeAnswers(String json) {
        JsonParser parser = new JsonParser();
        JsonObject rootObject = parser.parse(json).getAsJsonObject();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(HashMap.class, new AnswersDeserializer());
        Gson gson = gsonBuilder.create();
        Type type = new TypeToken<HashMap<Long, Answer>>(){}.getType();

        return gson.fromJson(rootObject, type);
    }

    public static HashMap<Long, Grade> deserializeGrades(String json) {
        JsonParser parser = new JsonParser();
        JsonObject rootObject = parser.parse(json).getAsJsonObject();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(HashMap.class, new GradesDeserializer());
        Gson gson = gsonBuilder.create();
        Type type = new TypeToken<HashMap<Long, Grade>>(){}.getType();

        return gson.fromJson(rootObject, type);
    }

    public static HashMap<Long, Question> deserializeQuestions(String json) {
        JsonParser parser = new JsonParser();
        JsonObject rootObject = parser.parse(json).getAsJsonObject();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(HashMap.class, new QuestionsDeserializer());
        Gson gson = gsonBuilder.create();
        Type type = new TypeToken<HashMap<Long, Question>>(){}.getType();

        return gson.fromJson(rootObject, type);
    }

}
