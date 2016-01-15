package com.jbrys.android.bloquery.api.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jeffbrys on 12/26/15.
 */
@ParseClassName("Answer")
public class Answer extends ParseObject {
    static final String QUESTION_ID = "questionId";
    static final String ANSWERER_ID = "answererId";
    static final String ANSWER_TEXT = "answerText";
    static final String UPVOTES = "upvotes";

    private String questionId;
    private String answererId;
    private String answerText;
    private int upvotes;

    public Answer(){
    }


    public String getQuestionId() {
        return getString(QUESTION_ID);
    }

    public void setQuestionId(String questionId) {
        put(QUESTION_ID, questionId);
    }

    public String getAnswererId() {
        return getString(ANSWERER_ID);
    }

    public void setAnswererId(String answererId) {
        put(ANSWERER_ID, answererId);
    }

    public String getAnswerText() {
        return getString(ANSWER_TEXT);
    }

    public void setAnswerText(String answerText) {
        put(ANSWER_TEXT, answerText);
    }

    public int getUpvotes() {
        return getInt(UPVOTES);
    }

    public void setUpvotes(int upvotes) {
        put(UPVOTES, upvotes);
    }

    public void incrementUpvotes() {
        increment(UPVOTES);
    }


}
