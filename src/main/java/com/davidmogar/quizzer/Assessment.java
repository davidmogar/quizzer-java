package com.davidmogar.quizzer;

import com.davidmogar.quizzer.domain.Answer;
import com.davidmogar.quizzer.domain.Grade;
import com.davidmogar.quizzer.domain.questions.Question;

import java.util.HashMap;
import java.util.List;

public class Assessment {

    private HashMap<Long, Question> questions;
    private HashMap<Long, List<Answer>> answers;
    private HashMap<Long, Grade> grades;

    public Assessment() {
        this(new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public Assessment(HashMap<Long, Question> questions, HashMap<Long, List<Answer>> answers,
            HashMap<Long, Grade> grades) {
        this.questions = questions;
        this.answers = answers;
        this.grades = grades;
    }

    /**
     * Calculate the grades of this assessment.
     */
    public void calculateGrades() {
        grades = new HashMap<>();

        for (long studentId : answers.keySet()) {
            grades.put(studentId, new Grade(studentId, calculateStudentGrade(studentId)));
        }
    }

    /**
     * Calculate the grade of a given student.
     * @param studentId Id of the student
     * @return Grade of the student
     */
    public double calculateStudentGrade(long studentId) {
        double grade = 0;

        if (answers.containsKey(studentId)) {
            for (Answer answer : answers.get(studentId)) {
                long questionId = answer.getQuestionId();
                if (questions.containsKey(questionId)) {
                    grade += questions.get(questionId).getScore(answer);
                }
            }
        }

        return grade;
    }

    public boolean validateGrade(Grade grade) {
        boolean valid = false;

        if (grade != null) {
            valid = grade.getGrade() == calculateStudentGrade(grade.getStudentId());
        }

        return valid;
    }

    public boolean validateGrades() {
        boolean valid = true;

        for (Grade grade : grades.values()) {
            if (valid = validateGrade(grade)) {
                break;
            }
        }

        return valid;
    }

    public HashMap<Long, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<Long, Question> questions) {
        this.questions = questions;
    }

    public HashMap<Long, List<Answer>> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<Long, List<Answer>> answers) {
        this.answers = answers;
    }

    public HashMap<Long, Grade> getGrades() {
        return grades;
    }

    public void setGrades(HashMap<Long, Grade> grades) {
        this.grades = grades;
    }

}
