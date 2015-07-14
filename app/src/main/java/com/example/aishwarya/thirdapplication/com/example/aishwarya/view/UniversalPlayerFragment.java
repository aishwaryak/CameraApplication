package com.example.aishwarya.thirdapplication.com.example.aishwarya.view;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.aishwarya.thirdapplication.R;
import com.example.aishwarya.thirdapplication.adapter.ViewsAdapter;
import com.example.aishwarya.thirdapplication.viewactivity.FullImageActivity;
import com.example.aishwarya.thirdapplication.viewactivity.FullVideoActivity;


/**
 * Created by Aishwarya on 7/2/2015.
 */
public class UniversalPlayerFragment extends Fragment {

    private GridView gridView;
    //grid view

    private Cursor cursor;
    //Cursor for accessing queries and results

    private final String MEDIA_TYPE_IMAGE_CONSTANT = "1";
    //Image constant for files

    private final String MEDIA_TYPE_VIDEO_CONSTANT = "3";
    //Image constant for videos

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = (LinearLayout) inflater.inflate(R.layout.universal_player, container, false);
        //inflate and get the view object

        String[] projection = {MediaStore.Files.FileColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.MEDIA_TYPE, MediaStore.Files.FileColumns.DATA};
        //Projection - to get the modified date, media type and location of files

        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        //Selection - select only images and video files from the entire list
        //1 media type - image, 3 media type - video


        final String orderBy = MediaStore.Files.FileColumns.DATE_MODIFIED;
        //Order string - to order by descending order of modified date - most recent first

        cursor = getActivity().managedQuery(MediaStore.Files.getContentUri("external"),
                projection,
                selection,
                null,
                orderBy + " DESC");// LIMIT 50");
        //Run query and get cursor

        /*String dataIndex = MediaStore.Files.FileColumns.DATA;
        int filePathColumn = cursor.getColumnIndexOrThrow(dataIndex);
        for(int i=0;i<cursor.getCount();i++) {
            cursor.moveToPosition(i);
            Log.i("Check",cursor.getString(filePathColumn));
        }*/

        gridView = (GridView) view.findViewById(R.id.gridView);
        //Grid View
        gridView.setAdapter(new ViewsAdapter(this.getActivity(), cursor,view));
        //Attaching adapter 'ViewsAdapter' for this grid

        gridView.setOnItemClickListener(new OnItemClickListener() {
            //on item click of every image in grid
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                cursor.moveToPosition(position);
                //Move the cursor to the respective position/row

                String mediaTypeIndex = MediaStore.Files.FileColumns.MEDIA_TYPE;
                int mediaTypeColumn = cursor.getColumnIndexOrThrow(mediaTypeIndex);
                //get the media type

                String dataIndex = MediaStore.Files.FileColumns.DATA;
                int filePathColumn = cursor.getColumnIndexOrThrow(dataIndex);
                String filePath = cursor.getString(filePathColumn);
                //get the location of the file

                if (MEDIA_TYPE_VIDEO_CONSTANT.equals(cursor.getString(mediaTypeColumn))) {
                    //MEDIA_TYPE = 1 means its an image, open the FullVideoActivity
                    Intent videoPlaybackActivity = new Intent(getActivity().getApplicationContext(), FullVideoActivity.class);
                    videoPlaybackActivity.putExtra("filePath", filePath);
                    startActivity(videoPlaybackActivity);

                } else if (MEDIA_TYPE_IMAGE_CONSTANT.equals(cursor.getString(mediaTypeColumn))) {
                    //MEDIA_TYPE = 3 means its a video, open the FullImageActivity
                    Intent imageOpenActivity = new Intent(getActivity().getApplicationContext(), FullImageActivity.class);
                    imageOpenActivity.putExtra("filePath", filePath);
                    startActivity(imageOpenActivity);
                }//End of if-else

            }//End of onItemClick
        }); //End of onItemClickListener

        return view;
    }//End of onCreateView

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
    }//End of onCreateOptionsMenu


}//End of UniversalPlayerFragment
