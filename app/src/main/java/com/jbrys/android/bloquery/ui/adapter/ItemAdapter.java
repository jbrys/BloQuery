package com.jbrys.android.bloquery.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jbrys.android.bloquery.BloQueryApplication;
import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.DataSource;
import com.jbrys.android.bloquery.api.model.Question;

/**
 * Created by jeffbrys on 12/8/15.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder> {


    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapterViewHolder holder, int position) {
        DataSource sharedDatasource = BloQueryApplication.getSharedDataSource();
        holder.update(sharedDatasource.getQuestionList().get(position));
    }

    @Override
    public int getItemCount() {
        return BloQueryApplication.getSharedDataSource().getQuestionList().size();
    }

    class ItemAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView questionView;
        TextView askerName;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);

            questionView = (TextView) itemView.findViewById(R.id.question_txt);
        }

        void update(Question question) {
            questionView.setText(question.getQuestionText());
        }
    }
}
