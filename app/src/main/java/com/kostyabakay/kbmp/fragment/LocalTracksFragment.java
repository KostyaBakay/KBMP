package com.kostyabakay.kbmp.fragment;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kostyabakay.kbmp.R;

/**
 * Created by Kostya on 10.04.2016.
 * This class represents the list of the user's local audio files.
 */
public class LocalTracksFragment extends Fragment {
    private ListView mListView;
    private MediaPlayer mMediaPlayer;
    private String[] mMusicList;
    private ArrayAdapter<String> mAdapter;

    public static LocalTracksFragment newInstance() {
        LocalTracksFragment fragment = new LocalTracksFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LocalTracksFragment.class.getSimpleName(), "onCreateView");
        return inflater.inflate(R.layout.fragment_local_tracks, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LocalTracksFragment.class.getSimpleName(), "onStart");
        mMediaPlayer = new MediaPlayer();
        setupUI();
        getLocalTracks();
    }

    /**
     * Initialization of view elements.
     */
    private void setupUI() {
        mListView = (ListView) getActivity().findViewById(R.id.local_tracks_list_view);
    }

    /**
     * Gets the user's local music tracks.
     */
    private void getLocalTracks() {
        mMusicList = getAudioList();
        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mMusicList);
        mListView.setAdapter(mAdapter);
    }

    /**
     * Gets list of the user's local audio files.
     */
    private String[] getAudioList() {
        final Cursor cursor = getActivity().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA},
                null, null, "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

        int count = cursor.getCount();
        String[] songs = new String[count];
        String[] audioPath = new String[count];
        int i = 0;

        if (cursor.moveToFirst()) {
            do {
                songs[i] = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                audioPath[i] = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                i++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        return songs;
    }
}
