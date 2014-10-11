package com.davidmogar.quizzer.domain.questions;

import com.davidmogar.quizzer.domain.Answer;

public abstract class Question {

    protected String text;
    protected long id;

    protected Question(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public abstract double getScore(Answer answer);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
