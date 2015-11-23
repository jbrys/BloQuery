package com.jbrys.android.bloquery;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by jeffbrys on 11/22/15.
 */
public class BloQueryApplication extends Application {

    public static BloQueryApplication getSharedInstance() {
        return sharedInstance;
    }

    private static BloQueryApplication sharedInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedInstance = this;

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "piFsT12m1vuIAklPVzM9SK4c1TsQygWM0jT1NYny", "wqn5T3dyxfCGDeXwTtI4MtUKwml9RJuchfjCzpkv");

    }


}
