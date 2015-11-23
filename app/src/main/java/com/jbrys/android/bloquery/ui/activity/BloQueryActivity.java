package com.jbrys.android.bloquery.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.model.User;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by jeffbrys on 11/23/15.
 */
public class BloQueryActivity extends AppCompatActivity{

    private final String TAG = getClass().getSimpleName();

    private EditText mEmail;
    private EditText mPassword;
    private CardView mLoginCardView;
    private TextView mSignupPrompt;

    private String emailTxt;
    private String passTxt;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bloquery_activity);

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
                Toast.makeText(getApplicationContext(), "Go to Signup form", Toast.LENGTH_LONG).show();
            }
        });

    }

}
