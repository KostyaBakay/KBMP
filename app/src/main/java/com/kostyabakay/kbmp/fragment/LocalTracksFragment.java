package com.kostyabakay.kbmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kostyabakay.kbmp.R;

/**
 * Created by Kostya on 10.04.2016.
 * This class represents the list of the user's local audio files.
 */
public class LocalTracksFragment extends Fragment {

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
}
