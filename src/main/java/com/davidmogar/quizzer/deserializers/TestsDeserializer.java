package com.davidmogar.quizzer.deserializers;

import com.davidmogar.quizzer.domain.Test;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestsDeserializer {

    @SuppressWarnings("unchecked")
    public static List<Test> deserialize(String json) {
        JsonParser parser = new JsonParser();
        JsonObject rootObject = parser.parse(json).getAsJsonObject();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(List.class, new TestsJsonDeserializer());
        Gson gson = gsonBuilder.create();

        return gson.fromJson(rootObject, List.class);
    }

}

class TestsJsonDeserializer implements JsonDeserializer<List<Test>> {

    @Override
    public List<Test> deserialize(JsonElement jsonElement, Type type,
                                                   JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        List<Test> tests = new ArrayList<>();

        JsonElement rootElement = jsonElement.getAsJsonObject().get("tests");

        for (JsonElement element : rootElement.getAsJsonArray()) {
            tests.add(createTest(element.getAsJsonObject()));
        }

        return tests;
    }

    private Test createTest(JsonObject object) {
        Test test = null;

        try {
            URL questionsUrl = new URL(object.get("quizz").getAsString());
            URL answersUrl = new URL(object.get("assessment").getAsString());
            URL gradesUrl = new URL(object.get("scores").getAsString());

            test = new Test(questionsUrl, answersUrl, gradesUrl);
        } catch(Exception e) {
            // Impossible to parse object. Skip it
        }

        return test;
    }

    private URL getURL(String path) {
        URL url = null;

        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            url = getClass().getResource(path);
        }

        return null;
    }

}
