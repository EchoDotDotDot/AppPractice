package com.echo.netese_arouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.echo.annotations.BindPath;
import com.echo.aroute.ARouter;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

    public void jumptootheractivity(View view) {
        ARouter.getInstance().jumpActivity("login/login",null);
    }
}
