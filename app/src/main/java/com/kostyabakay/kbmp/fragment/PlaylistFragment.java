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
public class PlaylistFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    public static PlaylistFragment newInstance() {
        PlaylistFragment f = new PlaylistFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }
}
