package com.example.aishwarya.thirdapplication.com.example.aishwarya.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.aishwarya.thirdapplication.R;
import com.example.aishwarya.thirdapplication.viewactivity.TakeImageActivity;
import com.example.aishwarya.thirdapplication.viewactivity.TakeVideoActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Aishwarya on 7/2/2015.
 */
public class TakePhotoFragment extends Fragment {


    private final static String DEVICE_TAG = "DEVICE_NAME";
    //Log TAG for printing the andrew id, device and model number, current time

    private final static int REQUEST_RESULT_IMAGE = 1;
    private final static int REQUEST_RESULT_VIDEO = 7;
    // Activity request codes - for the purpose of onActivityResult method

    private String filePath;
    //The file path - image or video

    ImageView imagePreview;
    VideoView videoPreview;
    //Previews of image and videos

    Button saveButton;
    Button discardButton;
    //Buttons for saving and discarding

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = (FrameLayout) inflater.inflate(R.layout.take_photo, container, false);
        // View that is inflated

        imagePreview = (ImageView) view.findViewById(R.id.imagePreview);
        videoPreview = (VideoView) view.findViewById(R.id.videoPreview);
        //Preview for images and videos

        saveButton = (Button) view.findViewById(R.id.btn_save);
        discardButton = (Button) view.findViewById(R.id.btn_discard);
        //Button for saving and discarding

        final Button takeImageButton = (Button) view.findViewById(R.id.btn_takepicture);
        takeImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeImageActivity = new Intent(getActivity().getApplicationContext(), TakeImageActivity.class);
                startActivityForResult(takeImageActivity, REQUEST_RESULT_IMAGE);

            }
        }); //Listener for image

        final Button takeVideoButton = (Button) view.findViewById(R.id.btn_takeVideo);
        takeVideoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeVideoActivity = new Intent(getActivity().getApplicationContext(), TakeVideoActivity.class);
                startActivityForResult(takeVideoActivity, REQUEST_RESULT_VIDEO);

            }
        }); //Listener for video

        return view;
    }

    /**
     * To toast a particular message
     *
     * @param message
     */
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        filePath = "";
        imagePreview.setVisibility(View.GONE);
        videoPreview.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        discardButton.setVisibility(View.GONE);
        //Initializing screen

        switch (requestCode) {
            case (REQUEST_RESULT_IMAGE):
                if (resultCode == Activity.RESULT_OK) {
                    filePath = data.getStringExtra("resultFilePath");
                    setButtonVisibility(filePath);
                    //Set the visibility of save and discard buttons
                    previewCapturedImage();
                    //To preview the image captured
                    saveButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveImage(filePath);
                        }
                    });//Save button listener

                    discardButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteFile(filePath);
                            showToast("Image Discarded!");
                        }
                    });//Discard button listener

                }
                break;
            case (REQUEST_RESULT_VIDEO):
                if (resultCode == Activity.RESULT_OK) {
                    filePath = data.getStringExtra("resultFilePath");
                    setButtonVisibility(filePath);
                    //Set the visibility of save and discard buttons
                    previewVideo();
                    //To preview the video captured
                    saveButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveVideo(filePath);
                        }
                    });//Save button listener

                    discardButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteFile(filePath);
                            showToast("Video Discarded!");
                        }
                    });//Discard button listener
                }
                break;
        }
    }

    /**
     * To set button visibility
     *
     * @param fileName
     */
    public void setButtonVisibility(String fileName) {
        File file = new File(fileName);

        if (file.exists()) {
            saveButton.setVisibility(View.VISIBLE);
            discardButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * To delete the file
     *
     * @param fileName
     */
    public void deleteFile(String fileName) {
        File file = new File(fileName);
        file.delete();
        saveButton.setVisibility(View.GONE);
        discardButton.setVisibility(View.GONE);
    }

    /**
     * To get the device name
     *
     * @return
     */
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    /**
     * For converting to upper case
     *
     * @param s
     * @return
     */
    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


    /**
     * To save the image
     *
     * @param sourceFilePath
     */
    private void saveImage(String sourceFilePath) {

        if (sourceFilePath != null && !sourceFilePath.equals("")) {

            File sourceFile = new File(sourceFilePath);
            //The temporary source file.

            String device = getDeviceName();
            String osVersion = Build.VERSION.RELEASE;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

            Date currentDate = new Date();
            String currentDateandTime = sdf.format(currentDate);
            String fileCurrentDateandTime = sdfNew.format(currentDate);

            String finalString = "aishwar1 : " + device + " " + osVersion + " : " + currentDateandTime;
            Log.i(DEVICE_TAG, finalString);

            String destinationFilePath = sourceFilePath.replace("TEMP", "PIC_" + fileCurrentDateandTime);

            File destinationFile = new File(destinationFilePath);
            if (sourceFile.exists()) {

                try {
                    InputStream in = new FileInputStream(sourceFile);
                    OutputStream out = new FileOutputStream(destinationFile);

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    //Delete the old temporary file
                    sourceFile.delete();

                    //Setting the content
                    ContentValues values = new ContentValues();

                    values.put(MediaStore.Files.FileColumns.MEDIA_TYPE, MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
                    values.put(MediaStore.Files.FileColumns.DATA, destinationFilePath);

                    getActivity().getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);

                    showToast("Image Saved Successfully!");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        saveButton.setVisibility(View.GONE);
        discardButton.setVisibility(View.GONE);

    }

    /**
     * To save the video
     *
     * @param sourceFilePath
     */
    private void saveVideo(String sourceFilePath) {

        if (sourceFilePath != null && !sourceFilePath.equals("")) {

            File sourceFile = new File(sourceFilePath);
            //The temporary source file.

            String device = getDeviceName();
            String osVersion = Build.VERSION.RELEASE;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

            Date currentDate = new Date();
            String currentDateandTime = sdf.format(currentDate);
            String fileCurrentDateandTime = sdfNew.format(currentDate);

            String finalString = "aishwar1 : " + device + " " + osVersion + " : " + currentDateandTime;
            Log.i(DEVICE_TAG, finalString);

            String destinationFilePath = sourceFilePath.replace("TEMP", "MOV_" + fileCurrentDateandTime);

            File destinationFile = new File(destinationFilePath);
            if (sourceFile.exists()) {

                try {
                    InputStream in = new FileInputStream(sourceFile);
                    OutputStream out = new FileOutputStream(destinationFile);

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    //Delete the old temporary file
                    sourceFile.delete();

                    ContentValues values = new ContentValues();

                    values.put(MediaStore.Files.FileColumns.MEDIA_TYPE, MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);
                    values.put(MediaStore.Files.FileColumns.DATA, destinationFilePath);

                    getActivity().getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);

                    showToast("Video Saved Successfully!");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        saveButton.setVisibility(View.GONE);
        discardButton.setVisibility(View.GONE);
    }

    /*
    * Display image from a path to ImageView
    */
    private void previewCapturedImage() {
        try {
            // hide video preview
            videoPreview.setVisibility(View.GONE);
            imagePreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath,
                    options);

            imagePreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /*
     * Previewing recorded video
     */
    private void previewVideo() {
        try {
            // hide image preview
            imagePreview.setVisibility(View.GONE);

            videoPreview.setVisibility(View.VISIBLE);
            videoPreview.setVideoPath(filePath);
            // start playing
            videoPreview.requestFocus();
            videoPreview.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
