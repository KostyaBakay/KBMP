package com.kostyabakay.kbmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kostyabakay.kbmp.R;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the view for playing music track.
 */
public class PlayTrackFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView songTimeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_track, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        SeekBar timelineSeekBar = (SeekBar) getActivity().findViewById(R.id.timeline_seekbar);
        songTimeTextView = (TextView) getActivity().findViewById(R.id.song_time_text_view);
        ImageView skipPreviousImageView = (ImageView) getActivity().findViewById(R.id.skip_previous_image_button);
        ImageView playImageView = (ImageView) getActivity().findViewById(R.id.play_image_button);
        ImageView skipNextImageView = (ImageView) getActivity().findViewById(R.id.skip_next_image_button);

        timelineSeekBar.setOnSeekBarChangeListener(this);
        skipPreviousImageView.setOnClickListener(this);
        playImageView.setOnClickListener(this);
        skipNextImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip_previous_image_button:
                Toast.makeText(getActivity(), "Back", Toast.LENGTH_SHORT).show();
                break;
            case R.id.play_image_button:
                Toast.makeText(getActivity(), "Play", Toast.LENGTH_SHORT).show();
                break;
            case R.id.skip_next_image_button:
                Toast.makeText(getActivity(), "Next", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        songTimeTextView.setText(String.valueOf(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public static PlayTrackFragment newInstance() {
        PlayTrackFragment f = new PlayTrackFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }
}
