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
import com.kostyabakay.kbmp.model.VkTrack;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;
import com.kostyabakay.kbmp.util.AppData;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

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
                AppData.isSongPlayed = true;
                Track track = mPlaylistAdapter.getItem(position);
                ((MainActivity) getActivity()).getViewPagerAdapter().setCurrentTrack(track);
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(1);

                if (AppData.isSongPlayed) AppData.mAudioPlayer.stop();

                VKRequest searchSongRequest = new VKRequest("audio.search", VKParameters.from(VKApiConst.Q, track.getName()));
                searchSongRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        ArrayList<VkTrack> trackList = VkTrack.parseJSON(response.responseString);
                        String song;

                        if (trackList != null) {
                            song = trackList.get(0).getArtist() + " - " + trackList.get(0).getTitle();
                            AppData.songUrl = trackList.get(0).getUrl();
                            Log.d(PlaylistFragment.class.getSimpleName(), song);
                            Log.d(PlaylistFragment.class.getSimpleName(), AppData.songUrl);
                        } else {
                            Log.e(PlaylistFragment.class.getSimpleName(), "trackList is null");
                        }

                        AppData.mAudioPlayer.play(getActivity(), AppData.songUrl);
                        AppData.isSongPlayed = true;
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(PlaylistFragment.class.getSimpleName(), "onDestroy");
        AppData.mAudioPlayer.stop();
    }
}
