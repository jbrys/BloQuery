package com.jbrys.android.bloquery.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.model.Answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeffbrys on 12/26/15.
 */
public class AnswerItemAdapter extends RecyclerView.Adapter<AnswerItemAdapter.AnswerItemAdapterViewHolder> {

    private List<Answer> mAnswerList = new ArrayList<>();

    public AnswerItemAdapter(List<Answer> answers) { mAnswerList = answers;}

    String votes;

    @Override
    public AnswerItemAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        votes = parent.getResources().getString(R.string.votes);
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item,
                parent, false);
        return new AnswerItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(AnswerItemAdapterViewHolder holder, int position) {
        holder.update(mAnswerList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAnswerList == null ? 0 : mAnswerList.size();
    }

    class AnswerItemAdapterViewHolder extends RecyclerView.ViewHolder {

        Answer mAnswer;

        TextView answerTextView;
        TextView votesTextView;

        public AnswerItemAdapterViewHolder(View itemView) {
            super(itemView);

            answerTextView = (TextView) itemView.findViewById(R.id.answer_txt);
            votesTextView = (TextView) itemView.findViewById(R.id.votes_txt);
        }

        void update(Answer answer) {
            this.mAnswer = answer;
            votes = String.format("%d %s", answer.getUpvotes(), votes);

            answerTextView.setText(answer.getAnswerText());
            votesTextView.setText(votes);
        }
    }
}
