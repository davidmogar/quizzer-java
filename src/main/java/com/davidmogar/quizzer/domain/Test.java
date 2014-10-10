package com.davidmogar.quizzer.domain;

import java.net.URL;

public class Test {

    private URL questionsUrl;
    private URL answersUrl;
    private URL gradesUrl;

    public Test(URL questionsUrl, URL answersUrl, URL gradesUrl) {
        this.questionsUrl = questionsUrl;
        this.answersUrl = answersUrl;
        this.gradesUrl = gradesUrl;
    }

    public URL getQuestionsUrl() {
        return questionsUrl;
    }

    public void setQuestionsUrl(URL questionsUrl) {
        this.questionsUrl = questionsUrl;
    }

    public URL getAnswersUrl() {
        return answersUrl;
    }

    public void setAnswersUrl(URL answersUrl) {
        this.answersUrl = answersUrl;
    }

    public URL getGradesUrl() {
        return gradesUrl;
    }

    public void setGradesUrl(URL gradesUrl) {
        this.gradesUrl = gradesUrl;
    }

}
