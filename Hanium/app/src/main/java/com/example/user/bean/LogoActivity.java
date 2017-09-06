package com.example.user.bean;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by user on 2017-05-24.
 */

public class LogoActivity extends AppCompatActivity {
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler(); //외부 뷰를 내부로 설정하기 위한 핸들러 객체 설정

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LogoActivity.this, LoginActivity.class);  //인텐트 객체 설정
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //FLAG 설정 타입은 New_TASK로 설정함
                startActivity(intent);  //인텐트를 통해 LogAcitivity로 화면 전환을 한다.
                finish();
            }
        }, 2000);
    }
}