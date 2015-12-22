package com.jbrys.android.bloquery.api;

import android.util.Log;

import com.jbrys.android.bloquery.api.model.Question;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jeffbrys on 12/8/15.
 */
public class DataSource {

    public static interface DataSourceChangedListener {
        public void onQuestionsLoaded(List<Question> questions);
        public void onRecentQuestionsLoaded(List<Question> questions);
    }

    private DataSourceChangedListener mListener;
    private List<Question> mQuestionList;

    public DataSource() {
        this.mListener = null;
        mQuestionList = new ArrayList<>();
//        createFakeData();
    }

    public void setDataSourceChangedListener(DataSourceChangedListener listener) {
        this.mListener = listener;
    }


    void createFakeData() {
        for (int i = 0; i < 10; i++) {
            mQuestionList.add(new Question("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ac felis suscipit, luctus eros vitae, tempus sem. Vestibulum et lorem euismod, luctus urna sit amet, gravida velit. In ac ex fringilla nisi rhoncus accumsan vitae non ipsum. Nunc in massa a purus feugiat maximus. Integer nec nibh odio. Suspendisse consequat, arcu ullamcorper cursus aliquam, turpis orci gravida massa, nec dapibus diam arcu a turpis. Quisque placerat felis massa, at molestie risus pulvinar at. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec lacus diam, gravida ut ante at, condimentum pretium libero. Pellentesque pretium nisi ultricies accumsan pharetra.",
                    "FooBar", 99, 0));
        }
    }


    public void loadQuestionsAsync() {
        ParseQuery<Question> query = ParseQuery.getQuery("Question");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(final List<Question> list, ParseException e) {
                if (e == null) {
                    Question.unpinAllInBackground("questions", new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            Question.pinAllInBackground("questions", list);
                        }
                    });
                    mListener.onQuestionsLoaded(list);


                } else {
                    logError(e);
                }
            }
        });
    }

    public void loadQuestionsFromLocal() {
        ParseQuery<Question> query = ParseQuery.getQuery("Question");
        query.orderByDescending("createdAt");
        query.fromLocalDatastore();

        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> list, ParseException e) {
                if (e == null){
                    mListener.onQuestionsLoaded(list);

                } else {
                    logError(e);
                }
            }
        });
    }

    public void loadQuestionsSince(Date date) {
        ParseQuery<Question> query = ParseQuery.getQuery("Question");
        query.whereGreaterThan("createdAt", date);

        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> objects, ParseException e) {
                if (e == null){
                    mListener.onRecentQuestionsLoaded(objects);

                } else {
                    logError(e);
                }
            }
        });
    }

    private void logError(ParseException e) {
        Log.e("Question", "Error: " + e.getMessage());
        e.printStackTrace();
    }

}
