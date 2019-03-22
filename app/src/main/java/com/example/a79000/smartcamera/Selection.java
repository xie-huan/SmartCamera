package com.example.a79000.smartcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Selection extends AppCompatActivity {

    Button btnTake;
    Button btnSelect;
    ImageView ivShow;
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        btnTake = (Button)findViewById(R.id.take);
        btnSelect = (Button)findViewById(R.id.select);
        photoFile = new File(getExternalFilesDir("img"), "scan.jpg");

        //when take photo button clicked
        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CropActivity.getJumpIntent(Selection.this, false, photoFile), 100);
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(CropActivity.getJumpIntent(Selection.this, true, photoFile), 100);
            }
        });
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
}
