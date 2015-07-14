package com.example.aishwarya.thirdapplication.viewactivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.example.aishwarya.thirdapplication.R;
import com.example.aishwarya.thirdapplication.com.example.aishwarya.view.TakePhotoFragment;
import com.example.aishwarya.thirdapplication.com.example.aishwarya.view.UniversalPlayerFragment;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        //Set the content view to the corresponding action bar

        android.app.ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //Get the action bar and set mode
        //Set the navigation mode for actionBar

        String universalPlayerLabel = getResources().getString(R.string.universalPlayerLabel);
        ActionBar.Tab universalPlayerTab = actionBar.newTab();
        universalPlayerTab.setText(universalPlayerLabel);
        //Universal Player - first tab

        TabListener<UniversalPlayerFragment> universalPlayerTabListener = new TabListener<>(this, universalPlayerLabel, UniversalPlayerFragment.class);
        universalPlayerTab.setTabListener(universalPlayerTabListener);
        actionBar.addTab(universalPlayerTab);
        //Adding actionTabListener for the 'universal player' tab

        String takePhotoLabel = getResources().getString(R.string.takePhotoLabel);
        ActionBar.Tab takePhotoTab = actionBar.newTab();
        takePhotoTab.setText(takePhotoLabel);
        //Take photo/video - second tab

        TabListener<TakePhotoFragment> takePhotoTabListener = new TabListener<>(this, takePhotoLabel, TakePhotoFragment.class);
        takePhotoTab.setTabListener(takePhotoTabListener);
        actionBar.addTab(takePhotoTab);
        //Adding actionTabListener for 'take photo' tab
    }//End of onCreate

    private class TabListener<T extends Fragment> implements ActionBar.TabListener {

        private Fragment mFragment;
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;

        public TabListener(Activity activity, String tag, Class<T> cls) {
            mActivity = activity;
            mTag = tag;
            mClass = cls;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            //To attach the fragment on selection
            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment); //Detach the fragment on un-selection
            }
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            //Nothing to do.
        }

    } //End of TabListener

}//End of mainActivity
