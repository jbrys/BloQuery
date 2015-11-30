package com.jbrys.android.bloquery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jbrys.android.bloquery.BloQueryApplication;
import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.model.User;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by jeffbrys on 11/26/15.
 */
public class LoginActivity extends AppCompatActivity {


    private final String TAG = getClass().getSimpleName();
    private final int SIGNUP_INTENT_CODE = 1;
    private final String SIGNUP_RESULT_USER_NAME = "USER NAME";
    private final String SIGNUP_RESULT_USER_PASSWORD = "PASSWORD";
    private final String SIGNUP_RESULT_USER_EMAIL = "EMAIL";

    private EditText mEmail;
    private EditText mPassword;
    private CardView mLoginCardView;
    private TextView mSignupPrompt;

    private String userNameTxt;
    private String emailTxt;
    private String passTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        mEmail = (EditText) findViewById(R.id.bloquery_txt_email);
        mPassword = (EditText) findViewById(R.id.bloquery_txt_password);

        mLoginCardView = (CardView) findViewById(R.id.bloquery_cv_login);
        mLoginCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailTxt = mEmail.getText().toString();
                passTxt = mPassword.getText().toString();

                User.logInInBackground(emailTxt, passTxt,
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    Toast.makeText(getApplicationContext(), "Ya dawg!", Toast.LENGTH_LONG)
                                            .show();
                                    BloQueryApplication.setCurrentUser(user);
                                    finish();
                                } else {

                                    Log.v(TAG, e.toString());
                                    Toast.makeText(getApplicationContext(), "Not a valid user. Please try again.", Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
            }
        });

        mSignupPrompt = (TextView) findViewById(R.id.bloquery_tv_signup_prompt);
        mSignupPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivityForResult(signUpIntent, SIGNUP_INTENT_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == SIGNUP_INTENT_CODE) {
            userNameTxt = data.getStringExtra(SIGNUP_RESULT_USER_NAME);
            passTxt = data.getStringExtra(SIGNUP_RESULT_USER_PASSWORD);


            User.logInInBackground(userNameTxt, passTxt,
                    new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            Toast.makeText(getApplicationContext(), "Ya dawg!", Toast.LENGTH_LONG)
                                    .show();
                            BloQueryApplication.setCurrentUser(user);
                            finish();
                        }
                    });


        }

    }

}
