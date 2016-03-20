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
import com.kostyabakay.kbmp.audio.AudioPlayer;
import com.kostyabakay.kbmp.model.chart.top.tracks.Artist;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the view for playing music track.
 */
public class PlayTrackFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView mArtistNameTextView, mSongNameTextView, mSongCurrentTimeTextView, mSongDurationTextView;
    private AudioPlayer mAudioPlayer = new AudioPlayer();
    private Track mTrack;
    private Artist mArtist;
    private SeekBar mTimelineSeekBar;
    private ImageView mSkipPreviousSongImageView, mPlaySongImageView, mSkipNextSongImageView;
    private boolean isSongPlayed;

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

        mPlaySongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSongPlayed) {
                    mAudioPlayer.play(getActivity());
                    mPlaySongImageView.setImageResource(R.mipmap.ic_pause_light);
                    isSongPlayed = true;
                } else {
                    mAudioPlayer.stop();
                    mPlaySongImageView.setImageResource(R.mipmap.ic_play_light);
                    isSongPlayed = false;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAudioPlayer.stop();
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

    public void updatePlayTrackFragment() {
        Track currentTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getCurrentTrack();
        if (currentTrack != null) {
            mTrack = currentTrack;
            mArtist = mTrack.getArtist();
            mArtistNameTextView.setText(mArtist.getName());
            mSongNameTextView.setText(mTrack.getName());
            mSongDurationTextView.setText(mTrack.getDuration());
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
