package com.davidmogar.quizzer.domain.questions;

import com.davidmogar.quizzer.domain.Answer;

import java.util.HashMap;

public class MultichoiceQuestion extends Question {

    private HashMap<Long, Alternative> alternatives;

    public MultichoiceQuestion() {
        alternatives = new HashMap<Long, Alternative>();
    }

    public void addAlternatives(long id, String text, double value) {
        alternatives.put(id, new Alternative(id, text, value));
    }

    @Override
    public double getScore(Answer answer) {
        return 0;
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
