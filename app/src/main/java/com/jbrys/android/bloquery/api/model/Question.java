package com.jbrys.android.bloquery.api.model;

/**
 * Created by jeffbrys on 12/8/15.
 */
public class Question {
    private String questionText;
    private String askerName;
    private int numAnswers;
    private int interest;

    public Question(String questionText, String askerName, int numAnswers, int interest) {
        this.questionText = questionText;
        this.askerName = askerName;
        this.numAnswers = numAnswers;
        this.interest = interest;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAskerName() {
        return askerName;
    }

    public void setAskerName(String askerName) {
        this.askerName = askerName;
    }

    public int getNumAnswers() {
        return numAnswers;
    }

    public void setNumAnswers(int numAnswers) {
        this.numAnswers = numAnswers;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }
}
