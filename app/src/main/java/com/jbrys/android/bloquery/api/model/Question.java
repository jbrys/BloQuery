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

    public Question(String questionText, String askerId, int numAnswers, int interest) {

        this.questionText = questionText;
        this.askerId = askerId;
        this.numAnswers = numAnswers;
        this.interest = interest;
    }

    public String getQuestionText() {
        return (questionText == null) ? getString("questionText") : questionText;
    }

    public void setQuestionText(String questionText) {
        if (this.questionText == null) {
            put("questionText", questionText);
        } else {
            this.questionText = questionText;
        }

    }

    public String getAskerId() {
        return (askerId == null) ? getString("askerId") : askerId;
    }

    public void setAskerId(String askerId) {
         if (this.askerId == null) {
             put("askerId", askerId);
         } else {
             this.askerId = askerId;
         }
    }

    public int getNumAnswers() {
        return numAnswers == 0 ? getInt("numAnswers") : numAnswers;
    }

    public void setNumAnswers(int numAnswers) {

        if (this.numAnswers == 0) {
            put("numAnswers", numAnswers);
        } else {
            this.numAnswers = numAnswers;
        }
    }

    public int getInterest() {
        return (this.interest == 0) ? getInt("interestScore") : interest;
    }

    public void setInterest(int interest) {
        if (this.interest == 0) {
            put("interestScore", interest);
        }else {
            this.interest = interest;
        }
    }

}
