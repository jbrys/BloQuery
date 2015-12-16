package com.jbrys.android.bloquery.api;

import android.util.Log;

import com.jbrys.android.bloquery.api.model.Question;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffbrys on 12/8/15.
 */
public class DataSource {

    private List<Question> mQuestionList;

    public DataSource() {
        mQuestionList = new ArrayList<Question>();
        ParseQuery<Question> query = ParseQuery.getQuery("Question");
        query.whereExists("questionText");
        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> list, ParseException e) {
                if (e == null) {
                    addQuestions(list);

                } else {
                    Log.e("Question", "Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

//        createFakeData();
    }

    public List<Question> getQuestionList() {
        return mQuestionList;
    }

    void createFakeData() {
        for (int i = 0; i < 10; i++) {
            mQuestionList.add(new Question("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ac felis suscipit, luctus eros vitae, tempus sem. Vestibulum et lorem euismod, luctus urna sit amet, gravida velit. In ac ex fringilla nisi rhoncus accumsan vitae non ipsum. Nunc in massa a purus feugiat maximus. Integer nec nibh odio. Suspendisse consequat, arcu ullamcorper cursus aliquam, turpis orci gravida massa, nec dapibus diam arcu a turpis. Quisque placerat felis massa, at molestie risus pulvinar at. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec lacus diam, gravida ut ante at, condimentum pretium libero. Pellentesque pretium nisi ultricies accumsan pharetra.",
                    "FooBar", 99, 0));
        }
    }

    void addQuestions (List<Question> list) {
        for (Question q : list){
            mQuestionList.add(q);
        }
    }

}
