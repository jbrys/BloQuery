package com.jbrys.android.bloquery.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatEditText;

import com.jbrys.android.bloquery.R;

/**
 * Created by jeffbrys on 1/2/16.
 */
public class AnswerQuestionDialog extends AppCompatDialogFragment {

    public interface AnswerDialogListener {
        public void onDialogPositiveClick(AppCompatDialogFragment dialog);
        public void onDialogNegativeClick(AppCompatDialogFragment dialog);
    }

    AnswerDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;

        try {
            mListener = (AnswerDialogListener) appCompatActivity.getSupportFragmentManager().findFragmentById(0);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
            + " must implement AnswerDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AnswerSubmit);

        builder.setView(R.layout.submit_answer_dialog)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppCompatEditText questionTextView;
                        String questionText;

                        questionTextView = (AppCompatEditText) getDialog().findViewById(R.id.et_answer_dialog);
                        questionText = questionTextView.getText().toString();

                        Bundle args = new Bundle();
                        args.putString(questionText, "questionText");

                        mListener.onDialogPositiveClick(AnswerQuestionDialog.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(AnswerQuestionDialog.this);
                    }
                });

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
