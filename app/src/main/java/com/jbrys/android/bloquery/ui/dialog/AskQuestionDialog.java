package com.jbrys.android.bloquery.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.jbrys.android.bloquery.R;

/**
 * Created by jeffbrys on 1/5/16.
 */
public class AskQuestionDialog extends AppCompatDialogFragment {

    public interface AskQuestionDialogListener{
        public void onDialogPositiveClick(AppCompatDialogFragment dialog);
    }

    AskQuestionDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            mListener = (AskQuestionDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getParentFragment().toString()
            + " must implement AskQuestionDialogListener");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogSubmit);
        builder.setView(R.layout.submit_question_dialog)
                .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(AskQuestionDialog.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }
}
