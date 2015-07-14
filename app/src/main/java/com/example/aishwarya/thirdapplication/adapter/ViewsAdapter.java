package com.example.aishwarya.thirdapplication.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.aishwarya.thirdapplication.R;

import java.io.File;

/**
 * Created by Aishwarya on 7/2/2015.
 */
public class ViewsAdapter extends BaseAdapter {

    private View view;
    private LayoutInflater layoutInflater;
    Cursor cursor;

    private final String MEDIA_TYPE_IMAGE_CONSTANT = "1";
    //Image constant for files

    private final String MEDIA_TYPE_VIDEO_CONSTANT = "3";
    //Image constant for videos

    public ViewsAdapter(Context localContext, Cursor paramterCursor, View paramView) {
        view = paramView;
        layoutInflater = (LayoutInflater) localContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = paramterCursor;
    }//End of constructor

    public int getCount() {
        return cursor.getCount();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.gridview_layout, null);
            //inflate
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon1);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        //setting options for bitmap factory

        cursor.moveToPosition(position);
        String mediaTypeIndex = MediaStore.Files.FileColumns.MEDIA_TYPE;
        int mediaTypeColumn = cursor.getColumnIndexOrThrow(mediaTypeIndex);
        //get the media type of the current file

        String dataIndex = MediaStore.Files.FileColumns.DATA;
        int filePathColumn = cursor.getColumnIndexOrThrow(dataIndex);
        String filePath = cursor.getString(filePathColumn);
        //get the location of the current file

        if (MEDIA_TYPE_VIDEO_CONSTANT.equals(cursor.getString(mediaTypeColumn))) {
            //Video files
            File file = new File(filePath);
            if (file.exists()) {

                ImageView playButtonView = (ImageView) convertView.findViewById(R.id.play_button);
                playButtonView.setVisibility(View.VISIBLE);
                Bitmap videoBitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.MICRO_KIND);
                imageView.setImageBitmap(videoBitmap);
            }
        } else if (MEDIA_TYPE_IMAGE_CONSTANT.equals(cursor.getString(mediaTypeColumn))) {

            ImageView playButtonView = (ImageView) convertView.findViewById(R.id.play_button);
            playButtonView.setVisibility(View.INVISIBLE);
            //Image files
            File file = new File(filePath);
            if (file.exists()) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(filePath, options));
            }
        }

        return convertView;
    }//End of getView
}//End of ViewsAdapter
