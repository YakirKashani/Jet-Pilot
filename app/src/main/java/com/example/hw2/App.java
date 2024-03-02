package com.example.hw2;

import android.app.Application;
import Utilities.SharedPreferencesManagerV3;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManagerV3.init(this);
    }
}
