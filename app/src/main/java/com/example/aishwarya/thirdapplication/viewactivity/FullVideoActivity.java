package com.example.aishwarya.thirdapplication.viewactivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.aishwarya.thirdapplication.R;

/**
 * Created by Aishwarya on 7/3/2015.
 */
public class FullVideoActivity extends Activity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, View.OnTouchListener {

    private VideoView mVV;
    //video view

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

        setContentView(R.layout.full_video);

        Intent intent = getIntent();
        String filePath = intent.getExtras().getString("filePath");
        //get intent data

        VideoView videoView = (VideoView) this.findViewById(R.id.full_video_view);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        //setting mediacontroller

        videoView.setVideoPath(filePath);
        //setting video file path

        videoView.requestFocus();
        videoView.start();
        //start
    }

    public void stopPlaying() {
        mVV.stopPlayback();
        this.finish();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        stopPlaying();
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setLooping(true);
    }
}