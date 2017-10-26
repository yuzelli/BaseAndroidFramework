package com.example.vpubao.baseandroidframework.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by DeepRolling on 2017/10/24.
 */

public class App extends Application {
    public static Context applicationContext;
    @Override
    public void onCreate() {
        applicationContext = this;
        super.onCreate();
    }

    public static Context getAppContext(){
        return applicationContext;
    }

}
