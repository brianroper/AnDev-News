package com.brianroper.androidweekly.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.brianroper.androidweekly.R;
import com.brianroper.androidweekly.adapters.VolumeAdapter;
import com.brianroper.androidweekly.model.ArchiveEvent;
import com.brianroper.androidweekly.model.Constants;
import com.brianroper.androidweekly.model.VolumeEvent;
import com.brianroper.androidweekly.presenters.VolumePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolumeActivity extends AppCompatActivity implements VolumeView {

    @BindView(R.id.volume_toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.volume_recycler)
    public RecyclerView mRecyclerView;

    private VolumePresenter mVolumePresenter;
    private VolumeAdapter mVolumeAdapter;
    private EventBus mEventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        ButterKnife.bind(this);

        initializePresenter();
        initializeAdapter();

        handleToolbarBehavior(mToolbar);
        handleAdapterDataSet();
    }

    /**
     * handles toolbar behavior
     */
    public void handleToolbarBehavior(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Android Weekly");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * initializes the presenter for this activity and attaches it to the view
     */
    public void initializePresenter(){
        mVolumePresenter = new VolumePresenter(getApplicationContext());
        mVolumePresenter.attachView(this);
        mVolumePresenter.startVolumeService(getVolumeId());
    }

    /**
     * gets the current volume id from the received intent
     */
    public int getVolumeId(){
        Intent archiveIntent = getIntent();
        int id = archiveIntent.getIntExtra("id", 0);
        return id;
    }

    /**
     * initializes the activities adapter
     */
    public void initializeAdapter(){
        mVolumeAdapter = new VolumeAdapter(getApplicationContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(mVolumeAdapter);
    }

    /**
     * handles the data set of the attached adapter
     */
    public void handleAdapterDataSet(){
        mVolumeAdapter.getVolumeDataFromRealm(getVolumeId());
        mVolumeAdapter.notifyDataSetChanged();
    }

    /**
     * watches for ArchiveEvent message data change throughout app
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onVolumeMessageEvent(VolumeEvent volumeEvent){
        Constants constants = new Constants();
        if(volumeEvent.message == constants.VOLUME_EVENT_FINISHED) {
            handleAdapterDataSet();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }
}
