package edu.cpp.l09_local_storage;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import io.paperdb.Paper;

/**
 * Created by yusun on 7/10/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);

        Paper.init(this);
    }

}
