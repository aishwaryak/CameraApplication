package com.example.aishwarya.thirdapplication.viewactivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.aishwarya.thirdapplication.R;
import com.example.aishwarya.thirdapplication.com.example.aishwarya.view.Camera2BasicFragment;

/**
 * Created by Aishwarya on 7/6/2015.
 */

public class TakeImageActivity extends Activity {
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.take_image);
        //Setting content view

        getFragmentManager().beginTransaction()
                .replace(R.id.takeImage_fragment, Camera2BasicFragment.newInstance()).commit();
        //Calling fragment

    }

}