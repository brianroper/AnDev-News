package com.brianroper.androidweekly.presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.brianroper.androidweekly.services.VolumeService;
import com.brianroper.androidweekly.views.VolumeView;

/**
 * Created by brianroper on 1/12/17.
 */

public class VolumePresenter implements Presenter<VolumeView> {

    private VolumeView mVolumeView;
    private Context mContext;

    public VolumePresenter(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(VolumeView view) {
        this.mVolumeView = view;
    }

    public void startVolumeService(int id){
        Intent volumeServiceIntent = new Intent(mContext, VolumeService.class);
        volumeServiceIntent.putExtra("id", id);
        mContext.startService(volumeServiceIntent);
    }
}
