package com.davidmogar.quizzer.domain.questions;

import com.davidmogar.quizzer.domain.Answer;

public class NumericalQuestion extends Question {

    private double correct;
    private double valueCorrect;
    private double valueIncorrect;

    public NumericalQuestion(long id, String text) {
        super(id, text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getScore(Answer answer) {
        double score = 0;

        try {
            score = (Double.parseDouble(answer.getValue()) == correct) ? valueCorrect : valueIncorrect;
        } catch (Exception e) {
            // Return default value
        }

        return score;
    }

    public double getCorrect() {
        return correct;
    }

    public void setCorrect(double correct) {
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
