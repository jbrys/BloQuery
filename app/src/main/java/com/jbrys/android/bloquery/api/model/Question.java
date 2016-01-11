package com.jbrys.android.bloquery.api.model;

import com.parse.FunctionCallback;
import com.parse.ParseClassName;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.HashMap;

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
        fetchNumAnswers();
        return getInt("numAnswers");
    }

    public void setNumAnswers(int numAnswers) {

        put("numAnswers", numAnswers);
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

    private void fetchNumAnswers() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("questionId", this.getObjectId());
        ParseCloud.callFunctionInBackground("numAnswers", params,
                new FunctionCallback<Integer>() {
                    @Override
                    public void done(Integer object, ParseException e) {
                        if (e == null) {
                            setNumAnswers(object);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });

    }

}
