package com.jbrys.android.bloquery.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.DataSource;
import com.jbrys.android.bloquery.api.model.Question;
import com.jbrys.android.bloquery.ui.adapter.ItemAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffbrys on 12/8/15.
 */
public class QuestionsFragment extends Fragment implements ItemAdapter.Listener {

    public static interface Listener {
        public void onItemAnswersClicked(QuestionsFragment questionsFragment, Question question);
    }

    private DataSource mDataSource;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private List<Question> mQuestionList = new ArrayList<>();

    private WeakReference<Listener> mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = new WeakReference<>((Listener) context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.questions_fragment, container, false);
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.rv_question_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.srl_question_list);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ItemAdapter(mQuestionList);
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mQuestionList == null)
                    return;

                mDataSource.loadQuestionsSince(mQuestionList.get(0).getCreatedAt());
            }
        });

        mDataSource = new DataSource();
        mDataSource.setDataSourceChangedListener(new DataSource.DataSourceChangedListener() {
            @Override
            public void onQuestionsLoaded(List<Question> questions) {
                for (Question q : questions) {
                    mQuestionList.add(q);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRecentQuestionsLoaded(List<Question> questions) {
                for (Question q : questions) {
                    mQuestionList.addAll(0, questions);
                }
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.notifyItemRangeInserted(0, questions.size());
                mRecyclerView.smoothScrollToPosition(0);
            }
        });

        mDataSource.loadQuestionsFromLocal();
        if (mQuestionList.isEmpty()) {
            mDataSource.loadQuestionsAsync();
        }



    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemAnswersClicked(ItemAdapter itemAdapter, Question question) {
        if (mListener != null) {
            mListener.get().onItemAnswersClicked(this, question);
        }
    }
}
