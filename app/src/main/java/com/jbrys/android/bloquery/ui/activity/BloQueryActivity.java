package com.jbrys.android.bloquery.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
public class BloQueryActivity extends AppCompatActivity implements QuestionsFragment.Listener{


    private final String TAG = getClass().getSimpleName();

    private ParseUser currentUser;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageView mDrawerHeaderBg;
    private TextView userNameTextView;
    private MenuItem answerItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bloquery_activity);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    ParseUser.logOutInBackground();
                    userNameTextView.setText(null);
                    startActivity(new Intent(BloQueryActivity.this, LoginActivity.class));
                    onDrawerItemClicked(item);
                    return true;
                }

                return false;
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (toolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        getSupportFragmentManager().beginTransaction()
                .add(R.id.bloquery_list_layout, new QuestionsFragment())
                .commit();

        currentUser = BloQueryApplication.getCurrentUser();

        View headerView = mNavigationView.getHeaderView(0);
        userNameTextView = (TextView) headerView.findViewById(R.id.bloquery_tv_userName);

        ImageView headerBg = (ImageView) headerView.findViewById(R.id.header_bg);
        headerBg.setImageResource(R.drawable.header_bg);
        headerBg.setScaleType(ImageView.ScaleType.CENTER_CROP);


        if (currentUser != null) {
            userNameTextView.setText(currentUser.getUsername());
        }


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



    @Override
    public void onItemAnswersClicked(QuestionsFragment questionsFragment, Question question) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.bloquery_list_layout, QuestionDetailFragment.detailFragmentForQuestion(question))
                .addToBackStack("ANSWER")
                .commit();

    }

    void onDrawerItemClicked(MenuItem item){
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }




}
