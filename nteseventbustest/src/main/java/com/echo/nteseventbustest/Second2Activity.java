package com.echo.nteseventbustest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Second2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getInstance().post(new TextBean("BALABALA","XIAOMOXIAN"));
                        Log.e("===SecondActivity====",Thread.currentThread().getName());
                    }
                }

                ).start();

            }
        });
    }
}
