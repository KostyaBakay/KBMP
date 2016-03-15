package com.kostyabakay.kbmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.adapter.PlaylistAdapter;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;

import java.util.ArrayList;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the list of different music tracks.
 */
public class PlaylistFragment extends Fragment {
    private PlaylistAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        ArrayList<Track> mTracks = new ArrayList<>();

        for (int i = 1; i < 51; i++) {
            Track c = new Track();
            c.setMbid("#" + i);
            mTracks.add(c);
        }

        adapter = new PlaylistAdapter(getActivity(), mTracks);
        ListView listView = (ListView) getActivity().findViewById(R.id.playlist_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), adapter.getItem(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static PlaylistFragment newInstance() {
        PlaylistFragment f = new PlaylistFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }
}
