package com.example.aishwarya.thirdapplication.viewactivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.aishwarya.thirdapplication.R;
import com.example.aishwarya.thirdapplication.com.example.aishwarya.view.Camera2VideoFragment;

/**
 * Created by Aishwarya on 7/7/2015.
 */
public class TakeVideoActivity extends Activity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.take_video);
        //Setting content View

        getFragmentManager().beginTransaction()
                .replace(R.id.takeVideo_fragment, Camera2VideoFragment.newInstance()).commit();
        //Calling fragment

    }

}
