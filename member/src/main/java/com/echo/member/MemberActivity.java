package com.echo.member;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.echo.annotations.BindPath;

@BindPath("member/member")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
    }
}
