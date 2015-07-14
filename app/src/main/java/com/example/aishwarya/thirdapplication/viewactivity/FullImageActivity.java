package com.example.aishwarya.thirdapplication.viewactivity;

/**
 * Created by Aishwarya on 7/3/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.aishwarya.thirdapplication.R;

public class FullImageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.full_image);

        Intent i = getIntent();
        String filePath = i.getExtras().getString("filePath");
        //Get intent data
        //Get file path

        Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageBitmap(myBitmap);
        //setting bitmap of image from the filepath
    }
}
