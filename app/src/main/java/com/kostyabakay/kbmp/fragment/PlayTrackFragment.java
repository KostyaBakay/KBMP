package com.kostyabakay.kbmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kostyabakay.kbmp.R;

/**
 * Created by Kostya on 10.03.2016.
 */
public class PlayTrackFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_track, container, false);
    }

    public static PlayTrackFragment newInstance() {
        PlayTrackFragment f = new PlayTrackFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }
}
