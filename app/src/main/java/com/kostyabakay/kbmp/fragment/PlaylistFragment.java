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
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;
import com.kostyabakay.kbmp.network.asynctask.GetTopTracksAsyncTask;
import com.kostyabakay.kbmp.network.asynctask.PlayTrackAsyncTask;
import com.kostyabakay.kbmp.util.AppData;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the list of different music tracks.
 */
public class PlaylistFragment extends Fragment {
    private PlaylistAdapter mPlaylistAdapter;
    private ListView mListView;
    private ArrayList<Track> mTracks = new ArrayList<>();
    private Track mCurrentTrack;
    private int mTrackPosition;

    public static PlaylistFragment newInstance() {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
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
        setupUI();
        getTopTracks();
        setTopTracksToListView();
        listenUI();
    }

    /**
     * Initialization of view elements.
     */
    private void setupUI() {
        mListView = (ListView) getActivity().findViewById(R.id.playlist_list_view);
    }

    /**
     * Gets top tracks from last.fm with chart.getTopTracks API method.
     */
    private void getTopTracks() {
        try {
            GetTopTracksAsyncTask getTopTracksAsyncTask = new GetTopTracksAsyncTask(getActivity());
            getTopTracksAsyncTask.execute().get();
            mTracks = getTopTracksAsyncTask.getTopTracks();
        } catch (InterruptedException e) {
            Log.e(PlaylistFragment.class.getSimpleName(), "InterruptedException");
        } catch (ExecutionException e) {
            Log.e(PlaylistFragment.class.getSimpleName(), "ExecutionException");
        }
    }

    /**
     * Sets ArrayList of top tracks to ListView using PlaylistAdapter.
     */
    private void setTopTracksToListView() {
        mPlaylistAdapter = new PlaylistAdapter(getActivity(), mTracks);
        ((MainActivity) getActivity()).getViewPagerAdapter().setTracks(mTracks);
        mListView.setAdapter(mPlaylistAdapter);
    }

    /**
     * This is listener for the user interface.
     */
    private void listenUI() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTrackPosition = position; // Makes global information about item position
                updateAudioPlayer();
                setCurrentTrack();
                playTrack();
                updateViewPager();
            }
        });
    }

    /**
     * Updates AudioPlayer data.
     */
    private void updateAudioPlayer() {
        if (AppData.isSongPlayed) AppData.audioPlayer.stop();
        AppData.isSongPlayed = true;
    }

    /**
     * Sets current track using information about track position.
     */
    private void setCurrentTrack() {
        mCurrentTrack = mPlaylistAdapter.getItem(mTrackPosition);
    }

    /**
     * Plays track from vk.com using data from last.fm.
     */
    private void playTrack() {
        PlayTrackAsyncTask playTrackAsyncTask = new PlayTrackAsyncTask(getActivity());
        playTrackAsyncTask.execute(mCurrentTrack.getName());
    }

    /**
     * Updates ViewPager corresponding of the user action.
     */
    private void updateViewPager() {
        ((MainActivity) getActivity()).getViewPagerAdapter().setCurrentTrack(mCurrentTrack);
        ((MainActivity) getActivity()).getViewPagerAdapter().setCurrentTrackItemIndex(mTrackPosition);
        ((MainActivity) getActivity()).getViewPager().setCurrentItem(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(PlaylistFragment.class.getSimpleName(), "onDestroy");
        AppData.audioPlayer.stop();
    }
}
