package com.davidmogar.quizzer.domain;


public class Answer {

    private long questionId;
    private String value;

    public Answer(long questionId, String value) {
        this.questionId = questionId;
        this.value = value;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
