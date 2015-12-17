package com.jbrys.android.bloquery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jbrys.android.bloquery.BloQueryApplication;
import com.jbrys.android.bloquery.R;
import com.parse.ParseUser;

/**
 * Created by jeffbrys on 11/23/15.
 */
public class BloQueryActivity extends AppCompatActivity implements Button.OnClickListener{

    private final String TAG = getClass().getSimpleName();

    private ParseUser currentUser;

    private TextView userNameTextView;
    private Button logout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bloquery_activity);

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
    public void onClick(View v) {
        ParseUser.logOutInBackground();
        userNameTextView.setText(null);
        startActivity(new Intent(BloQueryActivity.this, LoginActivity.class));

    }

}
