package com.brianroper.androidweekly.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brianroper.androidweekly.R;
import com.brianroper.androidweekly.model.Archive;
import com.brianroper.androidweekly.model.Volume;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by brianroper on 1/15/17.
 */

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.VolumeViewHolder> {

    private Context mContext;
    private RealmResults<Volume> mRealmResults;

    public VolumeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public VolumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View root = inflater.inflate(R.layout.volume_item, parent, false);
        final VolumeViewHolder volumeViewHolder = new VolumeViewHolder(root);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVolumeListener(volumeViewHolder);
            }
        });

        return volumeViewHolder;
    }

    @Override
    public void onBindViewHolder(VolumeViewHolder holder, int position) {
        holder.mVolumeHeadline.setText(mRealmResults.get(position).getHeadline());
        holder.mVolumeSource.setText(mRealmResults.get(position).getSource());
        holder.mVolumeSummary.setText(mRealmResults.get(position).getSummary());
    }

    @Override
    public int getItemCount() {
        return mRealmResults.size();
    }

    public class VolumeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.volume_headline)
        public TextView mVolumeHeadline;
        @BindView(R.id.volume_source)
        public TextView mVolumeSource;
        @BindView(R.id.volume_summary)
        public TextView mVolumeSummary;

        public VolumeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * retrieves volume data from realm database
     */
    public void getVolumeDataFromRealm(){
        Realm realm;
        Realm.init(mContext);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
        mRealmResults = realm.where(Volume.class).findAll();
    }

    /**
     * handles click behavior for recycler view item
     */
    public void setVolumeListener(VolumeViewHolder holder){
        //TODO: handle webview behavior
    }
}