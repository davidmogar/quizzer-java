package com.davidmogar.quizzer.serializers;

import com.davidmogar.quizzer.domain.Grade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class AssessmentXmlSerializer {

    public static String serializeGrades(HashMap<Long, Grade> grades) {
        String result = "<scores>\n";

        for (Grade grade : grades.values()) {
            result += "\t<score>\n\t\t<studentId>" + grade.getStudentId() + "</studentId>\n";
            result += "\t\t<value>" + grade.getGrade() + "</value>\n\t</score>\n";
        }

        return result + "</scores>";
    }

    public static String serializeStatistics(HashMap<Long, Integer> statistics) {
        String result = "<statistics>\n";

        for (Map.Entry<Long, Integer> entry : statistics.entrySet()) {
            result += "\t<item>\n\t\t<questionId>" + entry.getKey() + "</questionId>\n";
            result += "\t\t<correctAnswers>" + entry.getValue() + "</correctAnswrs>\n\t</item>\n";
        }

        return result + "</statistics>";
    }
}
