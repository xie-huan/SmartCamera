package com.example.a79000.smartcamera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeAgain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom_again);

        final Intent intent = new Intent(this,com.example.a79000.smartcamera.Selection.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                WelcomeAgain.this.finish();
            }

        };
        timer.schedule(task, 2000); // 2秒后执行
    }
}
