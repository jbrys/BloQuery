package com.jbrys.android.bloquery.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.DataSource;
import com.jbrys.android.bloquery.api.model.Question;
import com.jbrys.android.bloquery.ui.adapter.QuestionItemAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffbrys on 12/8/15.
 */
public class QuestionsFragment extends Fragment implements QuestionItemAdapter.Listener {

    public static interface Listener {
        public void onItemAnswersClicked(QuestionsFragment questionsFragment, Question question);
    }

    private DataSource mDataSource;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private QuestionItemAdapter mAdapter;
    private List<Question> mQuestionList = new ArrayList<>();

    private WeakReference<Listener> mListenerRef;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListenerRef = new WeakReference<>((Listener) activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
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

        mAdapter = new QuestionItemAdapter(mQuestionList);
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
        mDataSource.setQuestionChangedListener(new DataSource.QuestionChangedListener() {
            @Override
            public void onQuestionsLoaded(List<Question> questions) {
                for (Question q : questions) {
                    if (mQuestionList.contains(q)){
                        continue;
                    }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.questions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_ask_question) {
            showAskQuestionDialog();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemAnswersClicked(QuestionItemAdapter questionItemAdapter, Question question) {
        if (mListenerRef.get() != null) {
            mListenerRef.get().onItemAnswersClicked(this, question);
        }
    }

    private void showAskQuestionDialog() {
    }
}
