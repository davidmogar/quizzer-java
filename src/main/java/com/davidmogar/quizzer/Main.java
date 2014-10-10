package com.davidmogar.quizzer;

import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.loaders.AssessmentLoader;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Main {

    public Main() {
    }

    public HashMap<Long, Grade> calculateGrades(URL questionsUrl, URL answersURL) {
        HashMap<Long, Grade> grades = null;

        try {
            Assessment assessment = AssessmentLoader.loadAssessment(questionsUrl, answersURL, null);
            if (assessment != null) {
                assessment.calculateGrades();
                grades = assessment.getGrades();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grades;
    }

    public void validateAssessments(URL url) {

    }

    public static void main(String[] args) {
        System.out.println("Bien");
    }
}
