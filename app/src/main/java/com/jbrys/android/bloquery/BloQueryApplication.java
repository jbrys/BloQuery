package com.jbrys.android.bloquery;

import android.app.Application;

import com.jbrys.android.bloquery.api.DataSource;
import com.jbrys.android.bloquery.api.model.User;
import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by jeffbrys on 11/22/15.
 */
public class BloQueryApplication extends Application {

    public static BloQueryApplication getSharedInstance() {
        return sharedInstance;
    }

    private static BloQueryApplication sharedInstance;
    private DataSource dataSource;

    private static ParseUser currentUser = null;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedInstance = this;
        dataSource = new DataSource();

        Parse.enableLocalDatastore(this);
        ParseUser.registerSubclass(User.class);
        Parse.initialize(this, "piFsT12m1vuIAklPVzM9SK4c1TsQygWM0jT1NYny", "wqn5T3dyxfCGDeXwTtI4MtUKwml9RJuchfjCzpkv");

        if (User.getCurrentUser() != null)
            currentUser = User.getCurrentUser();
    }

    public static ParseUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(ParseUser currentUser) {
        BloQueryApplication.currentUser = currentUser;
    }

    public static DataSource getSharedDataSource() {
        return BloQueryApplication.getSharedInstance().getDataSource();
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
