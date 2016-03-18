package com.kostyabakay.kbmp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.model.chart.top.tracks.Artist;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;

import java.util.ArrayList;

/**
 * Created by Kostya on 15.03.2016.
 * This is adapter for PlaylistFragment.
 */
public class PlaylistAdapter extends ArrayAdapter<Track> {
    private Context mContext;
    private ArrayList<Track> mTracks;

    public PlaylistAdapter(Context context, ArrayList<Track> tracks) {
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
    public Track getItem(int position) {
        // Gets one element at index
        return mTracks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.playlist_item, parent, false);
        TextView songId = (TextView) view.findViewById(R.id.playlist_item_id);
        TextView artistName = (TextView) view.findViewById(R.id.playlist_artist_name);
        TextView songName = (TextView) view.findViewById(R.id.playlist_song_name);
        Track track = mTracks.get(position);
        Artist artist = track.getArtist();
        songId.setText(track.getMbid());
        artistName.setText(artist.getName());
        songName.setText(track.getName());
        return view;
    }

}
