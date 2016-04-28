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
 * This is adapter for the first page of View Pager.
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
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.playlist_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.artistName = (TextView) convertView.findViewById(R.id.playlist_artist_name);
            viewHolder.songName = (TextView) convertView.findViewById(R.id.playlist_song_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.track = mTracks.get(position);
        viewHolder.artist = null;

        if (viewHolder.track.getArtist() != null) {
            viewHolder.artist = viewHolder.track.getArtist();
        }

        if (viewHolder.artist != null) {
            viewHolder.artistName.setText(viewHolder.artist.getName());
        }

        viewHolder.songName.setText(viewHolder.track.getName());
        return convertView;
    }

    static class ViewHolder {
        Track track;
        Artist artist;
        TextView artistName;
        TextView songName;
    }
}
