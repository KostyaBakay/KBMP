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
import com.kostyabakay.kbmp.asynctask.GetTopTracksAsyncTask;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

        try {
            GetTopTracksAsyncTask getTopTracksAsyncTask = new GetTopTracksAsyncTask(getActivity());
            getTopTracksAsyncTask.execute().get();
            mTracks = getTopTracksAsyncTask.getTopTracks();
        } catch (InterruptedException e) {
            Log.e(PlaylistFragment.class.getSimpleName(), "InterruptedException");
        } catch (ExecutionException e) {
            Log.e(PlaylistFragment.class.getSimpleName(), "ExecutionException");
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
