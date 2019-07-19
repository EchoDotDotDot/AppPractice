package com.echo.nteseventbustest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        EventBus.getInstance().register(this);
        startActivity(new Intent(this,Second2Activity.class));
    }

    @Subscribe(threadmode=ThreadMode.BACKGROUD)
    public void fun1(TextBean textBean){
        Log.e("====MainActivity====",textBean.toString());
        Log.e("===MainActivity====",Thread.currentThread().getName());
    }
}
