package com.kostyabakay.kbmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.activity.MainActivity;
import com.kostyabakay.kbmp.model.VkTrack;
import com.kostyabakay.kbmp.model.chart.top.tracks.Artist;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;
import com.kostyabakay.kbmp.util.AppData;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the view for playing music track.
 */
public class PlayTrackFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView mArtistNameTextView, mSongNameTextView, mSongCurrentTimeTextView, mSongDurationTextView;
    private Track mPreviousTrack, mCurrentTrack, mNextTrack;
    private Artist mPreviousArtist, mCurrentArtist, mNextArtist;
    private SeekBar mTimelineSeekBar;
    private ImageView mSkipPreviousSongImageView, mPlaySongImageView, mSkipNextSongImageView;

    public static PlayTrackFragment newInstance() {
        PlayTrackFragment fragment = new PlayTrackFragment();
        Bundle b = new Bundle();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(PlayTrackFragment.class.getSimpleName(), "onCreateView");
        return inflater.inflate(R.layout.fragment_play_track, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(PlayTrackFragment.class.getSimpleName(), "onStart");
        mTimelineSeekBar = (SeekBar) getActivity().findViewById(R.id.timeline_seekbar);
        mArtistNameTextView = (TextView) getActivity().findViewById(R.id.play_track_artist_name_text_view);
        mSongNameTextView = (TextView) getActivity().findViewById(R.id.play_track_song_name_text_view);
        mSongCurrentTimeTextView = (TextView) getActivity().findViewById(R.id.play_track_song_current_time_text_view);
        mSongDurationTextView = (TextView) getActivity().findViewById(R.id.play_track_song_duration_text_view);
        mSkipPreviousSongImageView = (ImageView) getActivity().findViewById(R.id.skip_previous_song_image_button);
        mPlaySongImageView = (ImageView) getActivity().findViewById(R.id.play_song_image_button);
        mSkipNextSongImageView = (ImageView) getActivity().findViewById(R.id.skip_next_song_image_button);

        mTimelineSeekBar.setOnSeekBarChangeListener(this);
        mSkipPreviousSongImageView.setOnClickListener(this);
        mSkipNextSongImageView.setOnClickListener(this);

        mSkipPreviousSongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Track previousTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getPreviousTrack();
                mPreviousTrack = previousTrack;

                VKRequest searchSongRequest = new VKRequest("audio.search", VKParameters.from(VKApiConst.Q, mPreviousTrack.getName()));
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

                        AppData.audioPlayer.play(getActivity(), AppData.songUrl);
                        AppData.isSongPlayed = true;
                    }
                });

                updatePlayTrackFragmentPrevious();
            }
        });

        mPlaySongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppData.isSongPlayed) {
                    mPlaySongImageView.setImageResource(R.mipmap.ic_pause_light);
                    AppData.audioPlayer.play(getActivity(), AppData.songUrl);
                    AppData.isSongPlayed = true;
                } else {
                    mPlaySongImageView.setImageResource(R.mipmap.ic_play_light);
                    AppData.audioPlayer.stop();
                    AppData.isSongPlayed = false;
                }
            }
        });

        mSkipNextSongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Track nextTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getNextTrack();
                mNextTrack = nextTrack;
                VKRequest searchSongRequest = new VKRequest("audio.search", VKParameters.from(VKApiConst.Q, mNextTrack.getName()));

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

                        AppData.audioPlayer.play(getActivity(), AppData.songUrl);
                        AppData.isSongPlayed = true;
                    }
                });

                updatePlayTrackFragmentNext();
                
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(PlayTrackFragment.class.getSimpleName(), "onDestroy");
        AppData.audioPlayer.stop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip_previous_song_image_button:
                Toast.makeText(getActivity(), "Back", Toast.LENGTH_SHORT).show();
                break;
            case R.id.play_song_image_button:
                Toast.makeText(getActivity(), "Play", Toast.LENGTH_SHORT).show();
                break;
            case R.id.skip_next_song_image_button:
                Toast.makeText(getActivity(), "Next", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void updatePlayTrackFragmentPrevious() {
        Track currentTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getPreviousTrack();
        if (currentTrack != null) {
            mPreviousTrack = currentTrack;
            mPreviousArtist = mPreviousTrack.getArtist();
            mArtistNameTextView.setText(mPreviousArtist.getName());
            mSongNameTextView.setText(mPreviousTrack.getName());
            mSongDurationTextView.setText(mPreviousTrack.getDuration());
            updateView();
        }
    }

    public void updatePlayTrackFragment() {
        Track currentTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getCurrentTrack();
        if (currentTrack != null) {
            mCurrentTrack = currentTrack;
            mCurrentArtist = mCurrentTrack.getArtist();
            mArtistNameTextView.setText(mCurrentArtist.getName());
            mSongNameTextView.setText(mCurrentTrack.getName());
            mSongDurationTextView.setText(mCurrentTrack.getDuration());
            updateView();
        }
    }

    public void updatePlayTrackFragmentNext() {
        Track currentTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getNextTrack();
        if (currentTrack != null) {
            mNextTrack = currentTrack;
            mNextArtist = mNextTrack.getArtist();
            mArtistNameTextView.setText(mNextArtist.getName());
            mSongNameTextView.setText(mNextTrack.getName());
            mSongDurationTextView.setText(mNextTrack.getDuration());
            updateView();
        }
    }

    private void updateView() {
        if (AppData.isSongPlayed) {
            mPlaySongImageView.setImageResource(R.mipmap.ic_pause_light);
        } else {
            mPlaySongImageView.setImageResource(R.mipmap.ic_play_light);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mSongCurrentTimeTextView.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
