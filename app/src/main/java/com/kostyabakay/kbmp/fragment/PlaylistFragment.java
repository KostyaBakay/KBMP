package com.kostyabakay.kbmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.activity.MainActivity;
import com.kostyabakay.kbmp.adapter.PlaylistAdapter;
import com.kostyabakay.kbmp.model.chart.top.tracks.Artist;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;

import java.util.ArrayList;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the list of different music tracks.
 */
public class PlaylistFragment extends Fragment {
    private PlaylistAdapter mPlaylistAdapter;
    private ListView mListView;

    public static PlaylistFragment newInstance() {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle b = new Bundle();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(PlaylistFragment.class.getSimpleName(), "onCreateView");
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(PlaylistFragment.class.getSimpleName(), "onStart");
        ArrayList<Track> mTracks = new ArrayList<>();

        for (int i = 1; i < 51; i++) {
            Track track = new Track();
            Artist artist = new Artist();
            track.setMbid("#" + i);
            artist.setName("Artist: " + i);
            track.setArtist(artist);
            track.setName("Song: " + i);
            track.setDuration("Length of song: " + i);
            mTracks.add(track);
        }

        mPlaylistAdapter = new PlaylistAdapter(getActivity(), mTracks);
        mListView = (ListView) getActivity().findViewById(R.id.playlist_list_view);
        mListView.setAdapter(mPlaylistAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Track track = mPlaylistAdapter.getItem(position);
                ((MainActivity) getActivity()).getViewPagerAdapter().setCurrentTrack(track);
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(1);
            }
        });
    }
}
