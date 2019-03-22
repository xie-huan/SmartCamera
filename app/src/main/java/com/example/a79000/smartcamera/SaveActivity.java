package com.example.a79000.smartcamera;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.InputStream;

public class SaveActivity extends AppCompatActivity {

    ImageView croppedImage;
    File croppedFile;

    Button save;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        croppedImage = (ImageView)findViewById(R.id.croppedImage);
        save = (Button)findViewById(R.id.save);
        cancel = (Button)findViewById(R.id.cancel);

        croppedFile = (File) getIntent().getSerializableExtra("imageFile");

        Bitmap bitmap = BitmapFactory.decodeFile(croppedFile.getPath());
        croppedImage.setImageBitmap(bitmap);


        //cancel button is clicked.
        //This will make you back to the Selection Frame.
        final Intent cancel_intent = new Intent(this,Selection.class);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(cancel_intent);
            }
        });

        //save the image
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
