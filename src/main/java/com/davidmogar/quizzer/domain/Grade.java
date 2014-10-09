package com.davidmogar.quizzer.domain;

public class Grade {

    private long studentId;
    private double grade;

    public Grade(long studentId, double grade) {
        this.studentId = studentId;
        this.grade = grade;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

}
