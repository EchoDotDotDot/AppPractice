package com.echo.netese_arouter;

import android.app.Application;

import com.echo.aroute.ARouter;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
