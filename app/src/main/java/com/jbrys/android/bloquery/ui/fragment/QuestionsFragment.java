package com.jbrys.android.bloquery.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.DataSource;
import com.jbrys.android.bloquery.api.model.Question;
import com.jbrys.android.bloquery.ui.adapter.ItemAdapter;

import java.util.List;

/**
 * Created by jeffbrys on 12/8/15.
 */
public class QuestionsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private List<Question> mQuestionList;


    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.questions_fragment, container, false);
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.rv_question_list);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        DataSource dataSource = new DataSource();
        dataSource.setDataSourceChangedListener(new DataSource.DataSourceChangedListener() {
            @Override
            public void onDataLoaded(List<Question> questions) {
                for (Question q : questions) {
                    mQuestionList.add(q);
                }
            }
        });

        mAdapter = new ItemAdapter(mQuestionList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
