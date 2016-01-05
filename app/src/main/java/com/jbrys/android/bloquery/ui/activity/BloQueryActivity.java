package com.jbrys.android.bloquery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jbrys.android.bloquery.BloQueryApplication;
import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.model.Question;
import com.jbrys.android.bloquery.ui.fragment.QuestionDetailFragment;
import com.jbrys.android.bloquery.ui.fragment.QuestionsFragment;
import com.parse.ParseUser;

/**
 * Created by jeffbrys on 11/23/15.
 */
public class BloQueryActivity extends AppCompatActivity implements
        Button.OnClickListener,
        QuestionsFragment.Listener{
    private final String TAG = getClass().getSimpleName();

    private ParseUser currentUser;

    private Toolbar mToolbar;
    private TextView userNameTextView;
    private Button logout;
    private MenuItem answerItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bloquery_activity);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.bloquery_list_layout, new QuestionsFragment())
                .commit();

        currentUser = BloQueryApplication.getCurrentUser();

        userNameTextView = (TextView) findViewById(R.id.bloquery_tv_userName);

        if (currentUser != null) {
            userNameTextView.setText(currentUser.getUsername());
        }

        logout = (Button) findViewById(R.id.bloquery_btn_logout);
        logout.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        currentUser = BloQueryApplication.getCurrentUser();

        if (currentUser != null) {
            userNameTextView.setText(currentUser.getUsername());
        } else {
            Intent loginIntent = new Intent(BloQueryActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }


    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            if (answerItem != null) {
                answerItem.setVisible(false);
                answerItem.setEnabled(false);
            }

            getFragmentManager().popBackStack();
        }
    }



    /*
    * OnClickListeners
    */

    @Override
    public void onClick(View v) {
        ParseUser.logOutInBackground();
        userNameTextView.setText(null);
        startActivity(new Intent(BloQueryActivity.this, LoginActivity.class));

    }

    @Override
    public void onItemAnswersClicked(QuestionsFragment questionsFragment, Question question) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bloquery_list_layout, QuestionDetailFragment.detailFragmentForQuestion(question))
                .addToBackStack("ANSWER")
                .commit();

    }




}
