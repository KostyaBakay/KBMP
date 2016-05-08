package com.kostyabakay.kbmp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.activity.AboutArtistActivity;
import com.kostyabakay.kbmp.activity.AboutTrackActivity;
import com.kostyabakay.kbmp.activity.MainActivity;
import com.kostyabakay.kbmp.adapter.PlaylistAdapter;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;
import com.kostyabakay.kbmp.network.asynctask.GetTopTracksAsyncTask;
import com.kostyabakay.kbmp.network.vk.TracksSearcher;
import com.kostyabakay.kbmp.util.AppData;
import com.kostyabakay.kbmp.util.Constants;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the list of different music tracks.
 */
public class TopTracksFragment extends Fragment {
    public static final String TAG = "TopTracksFragment";
    private PlaylistAdapter mPlaylistAdapter;
    private ListView mListView;
    private ArrayList<Track> mTracks = new ArrayList<>();
    private Track mCurrentTrack;
    private int mTrackPosition;

    public static TopTracksFragment newInstance() {
        TopTracksFragment fragment = new TopTracksFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TopTracksFragment.class.getSimpleName(), "onCreate");
        if (savedInstanceState != null) {
            mTracks = savedInstanceState.getParcelableArrayList("tracks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TopTracksFragment.class.getSimpleName(), "onCreateView");
        return inflater.inflate(R.layout.fragment_top_tracks, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TopTracksFragment.class.getSimpleName(), "onStart");
        if (VKSdk.isLoggedIn()) {
            setupUI();
            setTopTracksToListView();
            getTopTracks();
            listenUI();
        } else {
            Toast.makeText(getActivity(), R.string.please_log_in_to_vk, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initialization of view elements.
     */
    private void setupUI() {
        mListView = (ListView) getActivity().findViewById(R.id.playlist_list_view);
        registerForContextMenu(mListView);
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
     * Gets top tracks from last.fm with chart.getTopTracks API method.
     */
    private void getTopTracks() {
        if (mTracks != null && !mTracks.isEmpty()) {
            mPlaylistAdapter = new PlaylistAdapter(getActivity(), mTracks);
            ((MainActivity) getActivity()).getViewPagerAdapter().setTracks(mTracks);
            mListView.setAdapter(mPlaylistAdapter);
        } else {
            GetTopTracksAsyncTask getTopTracksAsyncTask = new GetTopTracksAsyncTask(getActivity(),
                    mTracks, mPlaylistAdapter);
            getTopTracksAsyncTask.execute();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TopTracksFragment.class.getSimpleName(), "onSaveInstanceState");
        if (mTracks != null) {
            outState.putParcelableArrayList("tracks", mTracks);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Track track = null;
        Intent intent = null;
        MainActivity activity = (MainActivity) getActivity();
        if (activity.isNetworkConnected()) {
            switch (item.getItemId()) {
                case R.id.context_menu_info_about_artist:
                    track = mPlaylistAdapter.getItem(info.position);
                    intent = new Intent(getActivity(), AboutArtistActivity.class);
                    intent.putExtra("artist_name", track.getArtist().getName());
                    startActivity(intent);
                    return true;
                case R.id.context_menu_info_about_track:
                    track = mPlaylistAdapter.getItem(info.position);
                    intent = new Intent(getActivity(), AboutTrackActivity.class);
                    intent.putExtra("artist_name", track.getArtist().getName());
                    intent.putExtra("track_name", track.getName());
                    startActivity(intent);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        } else {
            Toast.makeText(getActivity(),
                    R.string.please_check_internet_connection, Toast.LENGTH_LONG).show();
            return false;
        }
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
        if (AppData.isSongPlayed) AppData.sAudioPlayer.stop();
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
        AppData.sPlayingTrackMode = Constants.NETWORK_PLAYING_TRACK_MODE;
        TracksSearcher tracksSearcher = new TracksSearcher(getActivity());
        tracksSearcher.searchTrack(createCurrentSongFullName());
        AppData.isSongPlayed = true;
    }

    /**
     * Creates full name of the song using artist name, splitter and song name.
     *
     * @return String with full song name.
     */
    private String createCurrentSongFullName() {
        return mCurrentTrack.getArtist().getName() + " - " + mCurrentTrack.getName();
    }

    /**
     * Updates ViewPager corresponding of the user action.
     */
    private void updateViewPager() {
        ((MainActivity) getActivity()).getViewPagerAdapter().setCurrentTrack(mCurrentTrack);
        ((MainActivity) getActivity()).getViewPagerAdapter()
                .setCurrentTrackItemIndex(mTrackPosition);
        ((MainActivity) getActivity()).getViewPager().setCurrentItem(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TopTracksFragment.class.getSimpleName(), "onDestroy");
        mTracks.clear();
        AppData.sAudioPlayer.stop();
    }
}
