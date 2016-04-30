package com.kostyabakay.kbmp.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.activity.AboutArtistActivity;
import com.kostyabakay.kbmp.activity.MainActivity;
import com.kostyabakay.kbmp.adapter.PlaylistAdapter;
import com.kostyabakay.kbmp.model.chart.top.tracks.Artist;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;
import com.kostyabakay.kbmp.util.AppData;
import com.kostyabakay.kbmp.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kostya on 10.04.2016.
 * This class represents the list of the user's local audio files.
 */
public class LocalTracksFragment extends Fragment {
    private PlaylistAdapter mPlaylistAdapter;
    private ListView mListView;
    private ArrayList<Track> mTracks = new ArrayList<>();
    private Track mCurrentTrack;

    public static LocalTracksFragment newInstance() {
        LocalTracksFragment fragment = new LocalTracksFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LocalTracksFragment.class.getSimpleName(), "onCreateView");
        return inflater.inflate(R.layout.fragment_local_tracks, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LocalTracksFragment.class.getSimpleName(), "onStart");
        setupUI();
        getLocalTracks();
        listenUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        mTracks.clear();
    }

    /**
     * Initialization of view elements.
     */
    private void setupUI() {
        mListView = (ListView) getActivity().findViewById(R.id.local_tracks_list_view);
        registerForContextMenu(mListView);
    }

    /**
     * Gets the user's local music tracks.
     */
    private void getLocalTracks() {
        mTracks = getAudioList();
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
                AppData.sTrackPosition = position;
                updateAudioPlayer();
                setCurrentTrack();
                play();
                updateViewPager();
            }
        });
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
        switch (item.getItemId()) {
            case R.id.info_about_artist:
                Track track = mPlaylistAdapter.getItem(info.position);
                Intent intent = new Intent(getActivity(), AboutArtistActivity.class);
                intent.putExtra("artist_name", track.getArtist().getName());
                startActivity(intent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Gets list of the user's local audio files.
     */
    private ArrayList<Track> getAudioList() {
        final Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.DATA}, null, null,
                "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

        int count = cursor.getCount();
        Track[] songs = new Track[count];
        AppData.sAudioPath = new String[count];
        int i = 0;

        if (cursor.moveToFirst()) {
            do {
                songs[i] = new Track();
                Artist artist = new Artist();
                artist.setName(cursor.getString(cursor.getColumnIndexOrThrow
                        (MediaStore.Audio.Media.ARTIST)));
                songs[i].setArtist(artist);
                songs[i].setName(cursor.getString(cursor.getColumnIndexOrThrow
                        (MediaStore.Audio.Media.TITLE)));
                AppData.sAudioPath[i] = cursor.getString(cursor.getColumnIndexOrThrow
                        (MediaStore.Audio.Media.DATA));
                i++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        ArrayList<Track> localTracks = new ArrayList<Track>(Arrays.asList(songs));

        return localTracks;
    }

    /**
     * Updates AudioPlayer data.
     */
    private void updateAudioPlayer() {
        if (AppData.isSongPlayed) AppData.sAudioPlayer.stop();
        AppData.isSongPlayed = true;
    }

    /**
     * Sets current track using information about track position.
     */
    private void setCurrentTrack() {
        mCurrentTrack = mPlaylistAdapter.getItem(AppData.sTrackPosition);
    }

    private void play() {
        try {
            if (AppData.sTrackPosition > 0) {
                AppData.sPreviousSongPath = AppData.sAudioPath[AppData.sTrackPosition - 1];
            }

            // TODO: NPE in the maximum track position
            AppData.sNextSongPath = AppData.sAudioPath[AppData.sTrackPosition + 1];
            playTrack(AppData.sAudioPath[AppData.sTrackPosition]);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays track from local path.
     */
    private void playTrack(String path)
            throws IllegalArgumentException, IllegalStateException, IOException {
        Log.d(LocalTracksFragment.class.getSimpleName(), "playTrack: " + path);
        AppData.sPlayingTrackMode = Constants.LOCAL_PLAYING_TRACK_MODE;
        AppData.sCurrentSongPath = path;
        AppData.sAudioPlayer.play(getActivity(), AppData.sCurrentSongPath);
        AppData.isSongPlayed = true;
    }

    /**
     * Updates ViewPager corresponding of the user action.
     */
    private void updateViewPager() {
        ((MainActivity) getActivity()).getViewPagerAdapter().setCurrentTrack(mCurrentTrack);
        ((MainActivity) getActivity())
                .getViewPagerAdapter().setCurrentTrackItemIndex(AppData.sTrackPosition);
        ((MainActivity) getActivity()).getViewPager().setCurrentItem(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LocalTracksFragment.class.getSimpleName(), "onDestroy");
        AppData.sAudioPlayer.stop();
    }
}
