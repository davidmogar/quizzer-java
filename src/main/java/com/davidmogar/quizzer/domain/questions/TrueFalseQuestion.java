package com.davidmogar.quizzer.domain.questions;

import com.davidmogar.quizzer.domain.Answer;

public class TrueFalseQuestion extends Question {

    private String feedback;
    private boolean correct;
    private double valueCorrect;
    private double valueIncorrect;

    public TrueFalseQuestion(long id, String questionText) {
        super(id, questionText);
    }

    @Override
    public double getScore(Answer answer) {
        return (Boolean.parseBoolean(answer.getValue()) == correct)? valueCorrect : valueIncorrect;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public double getValueCorrect() {
        return valueCorrect;
    }

    public void setValueCorrect(double valueCorrect) {
        this.valueCorrect = valueCorrect;
    }

    public double getValueIncorrect() {
        return valueIncorrect;
    }

    public void setValueIncorrect(double valueIncorrect) {
        this.valueIncorrect = valueIncorrect;
    }

}
