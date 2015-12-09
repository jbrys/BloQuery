package com.jbrys.android.bloquery.api.model;

import com.parse.ParseClassName;
import com.parse.ParseUser;

/**
 * Created by jeffbrys on 11/23/15.
 */
@ParseClassName("_User")
public class User extends ParseUser {

    String mUserName;
    String mPassword;
    String mEmail;

    public User() {

    }


    public User(String userName, String password) {
        setUsername(userName);
        setPassword(password);

    }



}
