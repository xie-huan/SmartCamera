package com.example.a79000.smartcamera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Selection extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks{

    Button btnTake;
    File photoFile;

    private static final int RC_CAMERA_PERM = 123;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        WelcomeActivity.activityList.add(this);


        btnTake = (Button)findViewById(R.id.take);
        photoFile = new File(getExternalFilesDir("img"), "scan.jpg");

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            @AfterPermissionGranted(RC_CAMERA_PERM)
            public void onClick(View v) {
                if (hasCameraPermission()) {
                    // Have permission, do the thing!
                    //Toast.makeText(Selection.this, "TODO: Camera things", Toast.LENGTH_LONG).show();
                    startActivityForResult(CropActivity.getJumpIntent(Selection.this, false, photoFile), 100);
                } else {
                    // Ask for one permission
                    EasyPermissions.requestPermissions(
                            Selection.this,
                            getString(R.string.rationale_camera),
                            RC_CAMERA_PERM,
                            Manifest.permission.CAMERA);
                }
              }
                //startActivityForResult(CropActivity.getJumpIntent(Selection.this, false, photoFile), 100);
        });

        btnTake.setOnLongClickListener(new LongClick());
    }

    private class LongClick implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View view){
            startActivityForResult(CropActivity.getJumpIntent(Selection.this, true, photoFile), 100);
            return true;
        }
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }
    @Override
    public void onRationaleAccepted(int requestCode) {
        Log.d("Selection", "onRationaleAccepted:" + requestCode);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Log.d("Selection", "onRationaleDenied:" + requestCode);
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
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.

        }
    }


    //click the ‘back’ button twice, you will back to your phone's home page.
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


}
