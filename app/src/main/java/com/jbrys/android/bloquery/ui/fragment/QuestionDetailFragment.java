package com.jbrys.android.bloquery.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.DataSource;
import com.jbrys.android.bloquery.api.model.Answer;
import com.jbrys.android.bloquery.api.model.Question;
import com.jbrys.android.bloquery.ui.adapter.AnswerItemAdapter;
import com.jbrys.android.bloquery.ui.dialog.AnswerQuestionDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffbrys on 12/23/15.
 */
public class QuestionDetailFragment extends Fragment implements AnswerQuestionDialog.AnswerDialogListener {

    private static final String QUESTION_ID = "questionId";
    private static final String QUESTION_ASKER = "askerId";
    private static final String QUESTION_TEXT = "questionText";

    private String mQuestionId;
    private String mAskerId;
    private String mQuestionText;

    private RecyclerView mRecyclerView;
    private TextView mQuestionTextView;
    private AnswerItemAdapter mAdapter;

    private DataSource mDataSource;
    private List<Answer> mAnswerList = new ArrayList<>();


    public static final QuestionDetailFragment detailFragmentForQuestion(Question question) {
        QuestionDetailFragment questionDetailFragment =
                new QuestionDetailFragment();

        Bundle args = new Bundle();
        args.putString(QUESTION_ID, question.getObjectId());
        args.putString(QUESTION_ASKER, question.getAskerId());
        args.putString(QUESTION_TEXT, question.getQuestionText());

        questionDetailFragment.setArguments(args);

        return questionDetailFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mQuestionId = args.getString(QUESTION_ID);
        mAskerId = args.getString(QUESTION_ASKER);
        mQuestionText = args.getString(QUESTION_TEXT);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.question_detail, container, false);

        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.rv_question_detail);

        mQuestionTextView = (TextView) inflate.findViewById(R.id.detail_question_txt);
        mQuestionTextView.setText(mQuestionText);

        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new AnswerItemAdapter(mAnswerList);
        mRecyclerView.setAdapter(mAdapter);

        mDataSource = new DataSource();
        mDataSource.setAnswerChangedListener(new DataSource.AnswerChangedListener() {
            @Override
            public void onAnswersLoaded(List<Answer> answers) {
                for (Answer a : answers) {
                    if (mAnswerList.contains(a)){
                        continue;
                    }
                    mAnswerList.add(a);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAnswerSubmitted(Answer answer) {
                mAnswerList.add(0, answer);
                mAdapter.notifyItemInserted(0);
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        mDataSource.loadAnswersFromQuestion(mQuestionId);

        mQuestionTextView.setText(mQuestionText);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.question_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_answer){
            showAnswerQuestionDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(AppCompatDialogFragment dialog) {
        AppCompatEditText answerTextView;
        String answerText;

        answerTextView = (AppCompatEditText) dialog.getDialog().findViewById(R.id.et_answer_dialog);
        answerText = answerTextView.getText().toString();
        mDataSource.submitAnswer(answerText, mQuestionId);
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(AppCompatDialogFragment dialog) {
        dialog.dismiss();
    }

    /*
    * private methods
    */

    public void showAnswerQuestionDialog() {
        AnswerQuestionDialog dialog = new AnswerQuestionDialog();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        dialog.setTargetFragment(this, 0);
        dialog.show(activity.getSupportFragmentManager(), "answer");
    }

}
