package com.jbrys.android.bloquery.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.model.Question;

/**
 * Created by jeffbrys on 12/23/15.
 */
public class QuestionDetailFragment extends Fragment {


    public static final QuestionDetailFragment detailFragmentForQuestion(Question question) {
        QuestionDetailFragment questionDetailFragment = new QuestionDetailFragment();
        return questionDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.question_detail_layout, container, false);
        return inflate;
    }
}
