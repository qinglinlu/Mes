package com.mes.android.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by monkey on 2017/5/27.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
//        LitePalApplication.initialize(context);
    }
    public static Context getContext(){
        return context;
    }
}
