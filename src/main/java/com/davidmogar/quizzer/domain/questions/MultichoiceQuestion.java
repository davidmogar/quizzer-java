package com.davidmogar.quizzer.domain.questions;

import com.davidmogar.quizzer.domain.Answer;

import java.util.HashMap;

public class MultichoiceQuestion extends Question {

    private HashMap<Long, Alternative> alternatives;

    public MultichoiceQuestion(long id, String questionText) {
        super(id, questionText);

        alternatives = new HashMap<Long, Alternative>();;
    }

    public void addAlternative(long id, String text, double value) {
        alternatives.put(id, new Alternative(id, text, value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getScore(Answer answer) {
        double score = 0;
        
        try {
            long alternativeId = Long.parseLong(answer.getValue());
            if (alternatives.containsKey(alternativeId)) {
                score = alternatives.get(alternativeId).value;
            }
        } catch(NumberFormatException e) {
            /* Just return score (already initialized) */
        }

        return score;
    }

}

class Alternative {

    String text;
    double value;
    private long id;

    public Alternative(long id, String text, double value) {
        this.id = id;
        this.text = text;
        this.value = value;
    }

}
