package com.example.a79000.smartcamera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final Intent intent = new Intent(this,WelcomeAgain.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                WelcomeActivity.this.finish();
            }

        };
        timer.schedule(task, 2000); // 2秒后执行
    }

    public void bntNext(View view) {
        startActivity(new Intent(this,WelcomeAgain.class));
    }
}
