package com.jbrys.android.bloquery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jbrys.android.bloquery.R;
import com.jbrys.android.bloquery.api.model.User;
import com.parse.ParseException;
import com.parse.SignUpCallback;

/**
 * Created by jeffbrys on 11/23/15.
 */
public class SignupActivity extends AppCompatActivity  {

    private final int SIGNUP_INTENT_CODE = 1;
    private final String SIGNUP_RESULT_USER_NAME = "USER NAME";
    private final String SIGNUP_RESULT_USER_PASSWORD = "PASSWORD";

    private CardView mSignUp;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;

    private String emailTxt;
    private String passwordTxt;
    private String confirmTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        mEmail = (EditText) findViewById(R.id.bloquery_txt_email);
        mPassword = (EditText) findViewById(R.id.bloquery_txt_password);
        mPasswordConfirm = (EditText) findViewById(R.id.bloquery_txt_password_confirm);

        mSignUp = (CardView) findViewById(R.id.bloquery_cv_sign_up);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailTxt = mEmail.getText().toString();
                passwordTxt = mPassword.getText().toString();
                confirmTxt = mPasswordConfirm.getText().toString();

                if (!passwordTxt.equals(confirmTxt)){
                    mPasswordConfirm.setError("Passwords do not match");

                } else {

                    User user = new User(emailTxt, passwordTxt);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra(SIGNUP_RESULT_USER_NAME, "foo");
                                resultIntent.putExtra(SIGNUP_RESULT_USER_PASSWORD, "bar");
                                setResult(SIGNUP_INTENT_CODE, resultIntent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }
            }
        });

    }
}
