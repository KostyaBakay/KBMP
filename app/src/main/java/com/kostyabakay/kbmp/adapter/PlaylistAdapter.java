package com.kostyabakay.kbmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;

import java.util.List;

/**
 * Created by Kostya on 15.03.2016.
 */
public class PlaylistAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<Track> mTracks;

    public PlaylistAdapter(Context context, List<Track> tracks) {
        super(context, R.layout.playlist_item);
        this.mContext = context;
        this.mTracks = tracks;
    }

    @Override
    public int getCount() {
        // Returns the number of list items
        return mTracks.size();
    }

    @Override
    public String getItem(int position) {
        // Gets one element at index
        return mTracks.get(position).getMbid();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.playlist_item, parent, false);
        TextView songName = (TextView)view.findViewById(R.id.playlist_item_id);
        Track track = mTracks.get(position);
        songName.setText(track.getMbid());
        return view;
    }

}
