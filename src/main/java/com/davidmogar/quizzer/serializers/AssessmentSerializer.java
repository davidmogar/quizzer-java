package com.davidmogar.quizzer.serializers;

import com.davidmogar.quizzer.domain.Grade;

import java.util.HashMap;

public class AssessmentSerializer {

    public enum Format {
        JSON, XML
    }

    public static String serializeGrades(HashMap<Long, Grade> grades, Format format) {
        String result;

        switch (format) {
            case XML:
                result = AssessmentXmlSerializer.serializeGrades(grades);
                break;
            default: /* Json */
                result = AssessmentJsonSerializer.serializeGrades(grades);
                break;
        }

        return result;
    }

    public static String serializeStatistics(HashMap<Long, Integer> statistics, Format format) {
        String result;

        switch (format) {
            case XML:
                result = AssessmentXmlSerializer.serializeStatistics(statistics);
                break;
            default: /* Json */
                result = AssessmentJsonSerializer.serializeStatistics(statistics);
                break;
        }

        return result;
    }
}
