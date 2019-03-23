package com.example.a79000.smartcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Random;

public class SaveActivity extends AppCompatActivity {

    ImageView croppedImage;
    File croppedFile;

    Button save;
    Button cancel;
    TextView success;

    int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        WelcomeActivity.activityList.add(this);

        croppedImage = (ImageView)findViewById(R.id.croppedImage);
        save = (Button)findViewById(R.id.save);
        cancel = (Button)findViewById(R.id.cancel);
        success = (TextView)findViewById(R.id.location);

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
                //the flag ensure you only save the picture once.
                if(flag == 1){
                    //save picture in mobile phone

                    //generate the filename randomly.
                    Random random = new Random();
                    String fileName = String.valueOf(random.nextInt(Integer.MAX_VALUE));

                    SavePicture.saveBmp2Gallery(SaveActivity.this,
                            BitmapFactory.decodeFile(croppedFile.getPath()),
                            fileName);

                    String picPath = Environment.getExternalStorageDirectory()
                            + File.separator + Environment.DIRECTORY_DCIM
                            + File.separator + "Camera" + File.separator+fileName;
                    Toast.makeText(SaveActivity.this, "Save successfully. ", Toast.LENGTH_LONG).show();
                    success.setText("The Location is:"+picPath);
                    flag = 0;
                }
                else{
                    //if the image has been saved. give tips to user.
                    Toast.makeText(SaveActivity.this, "Already saved. \n" +
                            "Find it in the location below.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
