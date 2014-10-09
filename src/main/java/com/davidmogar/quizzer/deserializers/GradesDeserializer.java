package com.davidmogar.quizzer.deserializers;


import com.davidmogar.quizzer.domain.Grade;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class GradesDeserializer implements JsonDeserializer<HashMap<Long, Grade>> {

    @Override
    public HashMap<Long, Grade> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<Long, Grade> grades = new HashMap<>();

        JsonElement rootElement = jsonElement.getAsJsonObject().get("scores");

        for (JsonElement element : rootElement.getAsJsonArray()) {
            Grade grade = createGrade(element.getAsJsonObject());
            if (grade != null) {
                grades.put(grade.getStudentId(), grade);
            }
        }

        return grades;
    }

    private Grade createGrade(JsonObject object) {
        Grade grade = null;

        try {
            grade = new Grade(object.get("studentId").getAsLong(), object.get("value").getAsDouble());
        } catch(Exception e) {
            // Impossible to parse object. Skip it
        }

        return grade;
    }

}
