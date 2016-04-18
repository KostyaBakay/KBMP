package com.kostyabakay.kbmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kostyabakay.kbmp.R;

/**
 * Created by Kostya on 18.04.2016.
 */
public class StartingFragment extends Fragment {

    public static StartingFragment newInstance() {
        StartingFragment fragment = new StartingFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(StartingFragment.class.getSimpleName(), "onCreateView");
        return inflater.inflate(R.layout.fragment_starting, container, false);
    }
}
