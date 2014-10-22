package com.davidmogar.quizzer.serializers;

import com.davidmogar.quizzer.domain.Grade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class AssessmentJsonSerializer {

    /**
     * Returns an string with the representation of the grades in JSON format.
     *
     * @param grades grades to serialize
     * @return an string with the representation in the desired format
     */
    public static String serializeGrades(HashMap<Long, Grade> grades) {
        JsonObject rootObject = new JsonObject();
        JsonArray scores = new JsonArray();

        for (Grade grade : grades.values()) {
            JsonObject score = new JsonObject();
            score.addProperty("studentId", grade.getStudentId());
            score.addProperty("value", grade.getGrade());
            scores.add(score);
        }

        rootObject.add("scores", scores);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(rootObject);
    }

    /**
     * Returns an string with the representation of the statistics in JSON format.
     *
     * @param statistics statistics to serialize
     * @return an string with the representation in the desired format
     */
    public static String serializeStatistics(HashMap<Long, Integer> statistics) {
        JsonObject rootObject = new JsonObject();
        JsonArray items = new JsonArray();

        for (Map.Entry<Long, Integer> entry : statistics.entrySet()) {
            JsonObject item = new JsonObject();
            item.addProperty("questionId", entry.getKey());
            item.addProperty("correctAnswers", entry.getValue());
            items.add(item);
        }

        rootObject.add("items", items);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(rootObject);
    }
}
