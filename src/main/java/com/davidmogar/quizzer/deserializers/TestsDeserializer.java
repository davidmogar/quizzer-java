package com.davidmogar.quizzer.deserializers;

import com.davidmogar.quizzer.domain.Test;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestsDeserializer {

    /**
     * Deserializes the JSON representation received as arguments to a list of Test objects.
     *
     * @param json JSON representation of Test objects
     * @return a list containing the tests deserialized
     */
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

/**
 * Utility class used to deserialize a JSON containing tests.
 */
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

    /**
     * Creates a Test object with the data obtained from the JsonObject.
     *
     * @param object JsonObject with the data of the Test
     * @return new Test
     */
    private Test createTest(JsonObject object) {
        Test test = null;

        try {
            URL questionsUrl = getUrl(object.get("quizz").getAsString());
            URL answersUrl = getUrl(object.get("assessment").getAsString());
            URL gradesUrl = getUrl(object.get("scores").getAsString());

            test = new Test(questionsUrl, answersUrl, gradesUrl);
        } catch (Exception e) {
            // Impossible to parse object. Skip it
        }

        return test;
    }

    /**
     * Returns the parsed URL. This method is needed to load resources from external servers,
     * files in the computer and files in the resources directory.
     *
     * @param path path of the file
     * @return URL of the file
     */
    private URL getUrl(String path) {
        URL url = null;

        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            url = getClass().getResource(path);
        }

        return url;
    }

}
