package com.jbrys.android.bloquery.api.model;

import com.parse.ParseUser;

/**
 * Created by jeffbrys on 11/23/15.
 */
public class User extends ParseUser {

    String mUserName;
    String mPassword;
    String mEmail;

    public User(String userName, String password, String email) {
        setUsername(userName);
        setPassword(password);
        setEmail(email);
    }



}
