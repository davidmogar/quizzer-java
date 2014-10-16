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
     * Calculates the grade of a given student.
     *
     * @param studentId id of the student
     * @return calculated grade of the student
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

    /**
     * Returns a HashMap mapping each question id with the number of correct answers of that question.
     *
     * @return a HashMap with the questions' statistics
     */
    public HashMap<Long, Integer> getStatistics() {
        HashMap<Long, Integer> statistics = new HashMap<>();

        for (long studentId : answers.keySet()) {
            for (Answer answer : answers.get(studentId)) {
                long questionId = answer.getQuestionId();
                if (questions.containsKey(questionId)) {
                    if (questions.get(questionId).getScore(answer) > 0) {
                        if (statistics.containsKey(questionId)) {
                            statistics.put(questionId, statistics.get(questionId) + 1);
                        } else {
                            statistics.put(questionId, 1);
                        }
                    }
                }
            }
        }

        return statistics;
    }

    /**
     * Validates the grade received as argument, checking that the value stored correspond to the grade obtained by
     * the student.
     *
     * @param grade grade to validate
     * @return <code>true</code> if the grade is valid, <code>false</code> otherwise
     */
    public boolean validateGrade(Grade grade) {
        boolean valid = false;

        if (grade != null) {
            valid = grade.getGrade() == calculateStudentGrade(grade.getStudentId());
        }

        return valid;
    }

    /**
     * Validate all the grades of this assessment, checking that all the values stored in each grade correspond to
     * the actual grade obtained by the students.
     *
     * @return <code>true</code> if all the grades are valid, <code>false</code> otherwise
     */
    public boolean validateGrades() {
        boolean valid = true;

        for (Grade grade : grades.values()) {
            if (!(valid = validateGrade(grade))) {
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
