package com.davidmogar.quizzer.domain.questions;

import com.davidmogar.quizzer.domain.Answer;

public abstract class Question {

    protected String questionText;
    protected long id;

    protected Question(long id, String questionText) {
        this.id = id;
        this.questionText = questionText;
    }

    public abstract double getScore(Answer answer);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }


}
