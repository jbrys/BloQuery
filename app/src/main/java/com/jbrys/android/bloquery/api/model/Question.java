package com.jbrys.android.bloquery.api.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jeffbrys on 12/8/15.
 */
@ParseClassName("Question")
public class Question extends ParseObject{
    private String questionText;
    private String askerId;
    private int numAnswers;
    private int interest;

    public Question(){
    }

    public Question(String questionText, String askerName, int numAnswers, int interest) {

        this.questionText = questionText;
        this.askerId = askerName;
        this.numAnswers = numAnswers;
        this.interest = interest;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAskerId() {
        return askerId;
    }

    public void setAskerId(String askerId) {
        this.askerId = askerId;
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
