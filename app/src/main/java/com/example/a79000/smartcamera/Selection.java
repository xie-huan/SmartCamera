package com.example.a79000.smartcamera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Selection extends AppCompatActivity {

    Button btnTake;
    File photoFile;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        WelcomeActivity.activityList.add(this);

        //ActivityCompat.requestPermissions(Selection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        //firstUse();

        btnTake = (Button)findViewById(R.id.take);
        photoFile = new File(getExternalFilesDir("img"), "scan.jpg");

        final GestureDetector gestureDetector = new GestureDetector(Selection.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {//click
                startActivityForResult(CropActivity.getJumpIntent(Selection.this, false, photoFile), 100);
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {//double click
                startActivityForResult(CropActivity.getJumpIntent(Selection.this, true, photoFile), 100);
                return super.onDoubleTap(e);
            }

            /**
             * double click
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return super.onDoubleTapEvent(e);
            }
        });

        //when take photo button clicked

        /*btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CropActivity.getJumpIntent(Selection.this, false, photoFile), 100);
            }
        });*/
        btnTake.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });


        while (!ActivityCompat.shouldShowRequestPermissionRationale(Selection.this,Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(Selection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 100 && photoFile.exists()) {
            Intent intent = new Intent(this,SaveActivity.class);
            intent.putExtra("imageFile",photoFile);
            startActivity(intent);
        }
    }


    //click the back button twice, you will back to your phone's home page.
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //Back HOME
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //when exit button clicked
    public void butExit(View view) {
        exit();
    }
    public void exit()
    {
        for(Activity act:WelcomeActivity.activityList)
        {
            act.finish();
        }

        System.exit(0);

    }

    //when share button clicked
    public void bntShare(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "This is my smart camera. GitHub Link:https://github.com/xx790008409/SmartCamera");//分享的标题
        intent.putExtra(Intent.EXTRA_TEXT, "This is my smart camera. GitHub Link:https://github.com/xx790008409/SmartCamera");//分享的内容
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "share"));
    }

    public void firstUse(){
        SharedPreferences sharedPreferences = getSharedPreferences("FirstUse", 0);
        Boolean first_run = sharedPreferences.getBoolean("Firstu", true);
        if (first_run) {
            ActivityCompat.requestPermissions(Selection.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            sharedPreferences.edit().putBoolean("Firstu", false).commit();
        }
    }

}
