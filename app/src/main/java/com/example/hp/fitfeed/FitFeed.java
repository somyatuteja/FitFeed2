package com.example.hp.fitfeed;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by DELL on 19-10-2016.
 */
public class FitFeed extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);


    }
}
