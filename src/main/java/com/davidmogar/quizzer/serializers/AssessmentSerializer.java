package com.davidmogar.quizzer.serializers;

import com.davidmogar.quizzer.domain.Grade;

import java.util.HashMap;

public class AssessmentSerializer {

    /**
     * Returns an string with the representation of the grades in the desired format.
     *
     * @param grades grades to serialize
     * @param format format of the output
     * @return an string with the representation in the desired format
     */
    public static String serializeGrades(HashMap<Long, Grade> grades, Format format) {
        String result;

        switch (format) {
            case XML:
                result = AssessmentXmlSerializer.serializeGrades(grades);
                break;
            default: /* Json */
                result = AssessmentJsonSerializer.serializeGrades(grades);
        }

        return result;
    }

    /**
     * Returns an string with the representation of the statistics in the desired format.
     *
     * @param statistics statistics to serialize
     * @param format     format of the output
     * @return an string with the representation in the desired format.
     */
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

    public enum Format {
        JSON, XML
    }
}
