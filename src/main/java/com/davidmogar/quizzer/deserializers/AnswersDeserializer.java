package com.davidmogar.quizzer.deserializers;


import com.davidmogar.quizzer.domain.Answer;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnswersDeserializer implements JsonDeserializer<HashMap<Long, List<Answer>>> {

    @Override
    public HashMap<Long, List<Answer>> deserialize(JsonElement jsonElement, Type type,
                                                   JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<Long, List<Answer>> answers = new HashMap<>();

        JsonElement rootElement = jsonElement.getAsJsonObject().get("items");

        for (JsonElement element : rootElement.getAsJsonArray()) {
            JsonObject object = element.getAsJsonObject();
            answers.put(object.get("studentId").getAsLong(), getAnswers(object.get("answers").getAsJsonArray()));

        }

        return answers;
    }

    /**
     * Returns a list of answers created from the data stored in the JsonObjects inside the array.
     *
     * @param array array of JsonObjects with answers data
     * @return list of answers
     */
    private List<Answer> getAnswers(JsonArray array) {
        ArrayList<Answer> answers = new ArrayList<>();

        for (JsonElement element : array) {
            try {
                JsonObject object = element.getAsJsonObject();
                answers.add(new Answer(object.get("question").getAsLong(), object.get("value").getAsString()));
            } catch (Exception e) {
                // Impossible to parse object. Skip it
            }
        }

        return answers;
    }

}
