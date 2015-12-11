package com.jbrys.android.bloquery.ui.adapter;

import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
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

    class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        boolean contentExpanded;

        TextView questionTextView;
        TextView askerName;
        TextView expandedQuestionTextView;

        public ItemAdapterViewHolder(View itemView) {
            super(itemView);

            questionTextView = (TextView) itemView.findViewById(R.id.question_txt);
            expandedQuestionTextView = (TextView) itemView.findViewById(R.id.question_expanded);
            questionTextView.setOnClickListener(this);
            expandedQuestionTextView.setOnClickListener(this);
        }

        void update(Question question) {
            questionTextView.setText(question.getQuestionText());
            expandedQuestionTextView.setText(question.getQuestionText());
        }


        /*
        * OnClickListener
        */
        @Override
        public void onClick(View v) {
            if (v == questionTextView || v == expandedQuestionTextView) {
                animateContent(!contentExpanded);

            }
        }

        /*
        * Private methods
        */

        private void animateContent(final boolean expand) {
            if ((expand && contentExpanded) || (!expand && !contentExpanded)) {
                return;
            }

            int startingHeight = expandedQuestionTextView.getMeasuredHeight();
            int finalHeight = questionTextView.getMeasuredHeight();
            if (expand) {

                startingHeight = finalHeight;
                expandedQuestionTextView.setAlpha(0f);
                expandedQuestionTextView.setVisibility(View.VISIBLE);

                expandedQuestionTextView.measure(
                        View.MeasureSpec.makeMeasureSpec(questionTextView.getWidth(), View.MeasureSpec.EXACTLY),
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                finalHeight = expandedQuestionTextView.getMeasuredHeight();
            } else {
                questionTextView.setVisibility(View.VISIBLE);
            }
            startAnimator(startingHeight, finalHeight, new ValueAnimator.AnimatorUpdateListener(){
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    float expandedAlpha = expand ? animatedFraction : 1f - animatedFraction;
                    float contentAlpha = 1f - expandedAlpha;

                    expandedQuestionTextView.setAlpha(expandedAlpha);
                    questionTextView.setAlpha(contentAlpha);

                    expandedQuestionTextView.getLayoutParams().height = animatedFraction == 1f ?
                            ViewGroup.LayoutParams.WRAP_CONTENT :
                            (Integer) valueAnimator.getAnimatedValue();

                    expandedQuestionTextView.requestLayout();
                    if (animatedFraction == 1f) {
                        if (expand) {
                            questionTextView.setVisibility(View.GONE);
                        } else {
                            expandedQuestionTextView.setVisibility(View.GONE);
                        }
                    }
                }
            });
            contentExpanded = expand;
        }

        private void startAnimator(int start, int end, ValueAnimator.AnimatorUpdateListener animatorUpdateListener){
            ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
            valueAnimator.addUpdateListener(animatorUpdateListener);

            valueAnimator.setDuration(questionTextView.getResources().getInteger(android.R.integer.config_mediumAnimTime));

            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.start();
        }
    }
}
