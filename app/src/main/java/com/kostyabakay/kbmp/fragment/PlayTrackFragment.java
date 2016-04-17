package com.kostyabakay.kbmp.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.kostyabakay.kbmp.model.chart.top.tracks.Artist;
import com.kostyabakay.kbmp.model.chart.top.tracks.Image;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;
import com.kostyabakay.kbmp.network.asynctask.DownloadArtistImageAsyncTask;
import com.kostyabakay.kbmp.network.asynctask.PlayTrackAsyncTask;
import com.kostyabakay.kbmp.util.AppData;
import com.kostyabakay.kbmp.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the view for playing music track.
 */
public class PlayTrackFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private final int FIRST_SONG_INDEX = 0;
    private final int LAST_SONG_INDEX = 49;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private TextView mArtistNameTextView, mSongNameTextView, mSongCurrentTimeTextView, mSongDurationTextView;
    private ArrayList<Track> mTracks;
    private Track mPreviousTrack, mCurrentTrack, mNextTrack;
    private Artist mCurrentArtist;
    private SeekBar mTimelineSeekBar;
    private ImageView mSkipPreviousSongImageView, mPlaySongImageView, mSkipNextSongImageView, mArtistImageView;
    private int mCurrentTrackPosition, mTotalDuration;

    public static PlayTrackFragment newInstance() {
        PlayTrackFragment fragment = new PlayTrackFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
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
        setupUI();
    }

    /**
     * Initialization of view elements.
     */
    private void setupUI() {
        setupSeekBar();
        setupTextView();
        setupImageView();
        listenViewPagerChanges();
        listenImageViewButtons();
    }

    /**
     * Initialization of the SeekBar.
     */
    private void setupSeekBar() {
        mTimelineSeekBar = (SeekBar) getActivity().findViewById(R.id.timeline_seekbar);
        mTimelineSeekBar.setOnSeekBarChangeListener(this);
    }

    private void listenViewPagerChanges() {
        ((MainActivity) getActivity()).getViewPager().setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    Log.d(PlayTrackFragment.class.getSimpleName(), "onPageSelected");
                    updatePlayTrackFragment();
                    listenSeekBar();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void listenSeekBar() {
        AppData.audioPlayer.getMediaPlayer().setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                mTotalDuration = mp.getDuration();
                mTimelineSeekBar.setMax(mTotalDuration);
                mHandler.postDelayed(runnable, 100);
            }
        });
    }

    /**
     * Initialization of the TextView views.
     */
    private void setupTextView() {
        mArtistNameTextView = (TextView) getActivity().findViewById(R.id.play_track_artist_name_text_view);
        mSongNameTextView = (TextView) getActivity().findViewById(R.id.play_track_song_name_text_view);
        mSongCurrentTimeTextView = (TextView) getActivity().findViewById(R.id.play_track_song_current_time_text_view);
        mSongDurationTextView = (TextView) getActivity().findViewById(R.id.play_track_song_duration_text_view);
    }

    /**
     * Initialization of the ImageView views.
     */
    private void setupImageView() {
        mArtistImageView = (ImageView) getActivity().findViewById(R.id.play_track_artist_image_view);
        mSkipPreviousSongImageView = (ImageView) getActivity().findViewById(R.id.skip_previous_song_image_button);
        mPlaySongImageView = (ImageView) getActivity().findViewById(R.id.play_song_image_button);
        mSkipNextSongImageView = (ImageView) getActivity().findViewById(R.id.skip_next_song_image_button);
    }

    /**
     * There are listeners for ImageView buttons.
     */
    private void listenImageViewButtons() {
        listenPreviousSongButton();
        listenPlaySongButton();
        listenNextSongButton();
    }

    /**
     * This is listener for button which chooses previous song.
     */
    private void listenPreviousSongButton() {
        mSkipPreviousSongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppData.audioPlayer.getMediaPlayer() != null) {
                    getCurrentTrack();
                    changePreviousTrackToCurrent();
                    updateViewPagerAdapter(mPreviousTrack, mCurrentTrackPosition);
                    updateArtistImage(mPreviousTrack);
                    AppData.currentSongPath = AppData.previousSongPath;
                    playSong(createPreviousSongFullName());
                    updatePlayTrackFragment();
                } else {
                    Toast.makeText(getActivity(), "Please choose song!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This is listener for button which plays song or makes pause.
     */
    private void listenPlaySongButton() {
        mPlaySongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppData.audioPlayer.getMediaPlayer() != null) {
                    if (!AppData.isSongPlayed) {
                        mPlaySongImageView.setImageResource(R.mipmap.ic_pause);
                        AppData.audioPlayer.resume();
                        AppData.isSongPlayed = true;
                    } else {
                        mPlaySongImageView.setImageResource(R.mipmap.ic_play);
                        AppData.audioPlayer.pause();
                        AppData.isSongPlayed = false;
                    }
                } else {
                    Toast.makeText(getActivity(), "Please choose song!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This is listener for button which chooses next song.
     */
    private void listenNextSongButton() {
        mSkipNextSongImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppData.audioPlayer.getMediaPlayer() != null) {
                    getCurrentTrack();
                    changeNextTrackToCurrent();
                    updateViewPagerAdapter(mNextTrack, mCurrentTrackPosition);
                    updateArtistImage(mNextTrack);
                    AppData.currentSongPath = AppData.nextSongPath;
                    playSong(createNextSongFullName());
                    updatePlayTrackFragment();
                } else {
                    Toast.makeText(getActivity(), "Please choose song!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Gets chosen track and his position from tracks list at ViewPager.
     */
    private void getCurrentTrack() {
        mTracks = ((MainActivity) getActivity()).getViewPagerAdapter().getTracks();
        mCurrentTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getCurrentTrack();
        mCurrentTrackPosition = ((MainActivity) getActivity()).getViewPagerAdapter().getCurrentTrackItemIndex();
    }

    /**
     * Creates full name of the previous song using artist name, splitter and song name.
     *
     * @return String with full song name.
     */
    private String createPreviousSongFullName() {
        if (mPreviousTrack.getArtist() != null) {
            return mPreviousTrack.getArtist().getName() + " - " + mPreviousTrack.getName();
        } else {
            return mPreviousTrack.getName();
        }
    }

    /**
     * Creates full name of the next song using artist name, splitter and song name.
     *
     * @return String with full song name.
     */
    private String createNextSongFullName() {
        if (mNextTrack.getArtist() != null) {
            return mNextTrack.getArtist().getName() + " - " + mNextTrack.getName();
        } else {
            return mNextTrack.getName();
        }
    }

    /**
     * Previous track becomes current track.
     */
    private void changePreviousTrackToCurrent() {
        if (mCurrentTrackPosition > FIRST_SONG_INDEX) {
            mPreviousTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getPreviousTrack(mCurrentTrackPosition);
            mCurrentTrackPosition--;
        } else {
            mPreviousTrack = mCurrentTrack;
        }
    }

    /**
     * Next track becomes current track.
     */
    private void changeNextTrackToCurrent() {
        if (mCurrentTrackPosition < LAST_SONG_INDEX) {
            mNextTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getNextTrack(mCurrentTrackPosition);
            mCurrentTrackPosition++;
        } else {
            mNextTrack = mCurrentTrack;
        }
    }

    /**
     * Updates ViewPagerAdapter with current track.
     *
     * @param track
     * @param position
     */
    private void updateViewPagerAdapter(Track track, int position) {
        ((MainActivity) getActivity()).getViewPagerAdapter().setCurrentTrack(track);
        ((MainActivity) getActivity()).getViewPagerAdapter().setCurrentTrackItemIndex(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(PlayTrackFragment.class.getSimpleName(), "onDestroy");
        AppData.audioPlayer.stop();
    }

    /**
     * Plays song from vk.com with corresponding name.
     *
     * @param trackName
     */
    private void playSong(String trackName) {
        if (AppData.playingTrackMode == Constants.NETWORK_PLAYING_TRACK_MODE) {
            PlayTrackAsyncTask playTrackAsyncTask = new PlayTrackAsyncTask(getActivity());
            playTrackAsyncTask.execute(trackName);
        } else if (AppData.playingTrackMode == Constants.LOCAL_PLAYING_TRACK_MODE) {
            AppData.audioPlayer.play(getActivity(), AppData.currentSongPath);
        }
    }

    /**
     * Updates PlayTrackFragment with information about current track.
     */
    public void updatePlayTrackFragment() {
        if (AppData.playingTrackMode == Constants.LOCAL_PLAYING_TRACK_MODE) {
            clearPlayTrackFragment();
        }

        mCurrentTrack = ((MainActivity) getActivity()).getViewPagerAdapter().getCurrentTrack();
        if (mCurrentTrack != null) {

            if (mCurrentTrack.getArtist() != null) {
                mCurrentArtist = mCurrentTrack.getArtist();
                mArtistNameTextView.setText(mCurrentTrack.getArtist().getName());
            }

            mSongNameTextView.setText(mCurrentTrack.getName());
            mSongDurationTextView.setText(mCurrentTrack.getDuration());
            updateArtistImage(mCurrentTrack); // TODO: Fix double call method
            updatePlayButton();
        }
    }

    /**
     * Clears PlayTrackFragment.
     */
    public void clearPlayTrackFragment() {
        mArtistImageView.setImageResource(R.drawable.last_fm_logo);
        mArtistNameTextView.setText("");
        mSongNameTextView.setText("");
        mSongCurrentTimeTextView.setText(R.string.zero_time);
        mSongDurationTextView.setText(R.string.zero_time);
    }

    /**
     * This method updates artist image on PlayTrackFragment. Every track on Last.fm contains a few
     * same images with artist, but these images have different quality. They stored in list and
     * the first element contain URL for low quality image, the second for better quality, the third
     * for much better quality and etc. The last element of list contains URL for the best quality
     * of artist image.
     * Later we pass URL of the best quality image to AsyncTask and this AsyncTask will download
     * this image and update PlayTrackFragment with this artist image.
     */
    private void updateArtistImage(Track track) {
        if (AppData.playingTrackMode == Constants.NETWORK_PLAYING_TRACK_MODE) {
            List<Image> images = track.getImage();
            Image image = images.get(images.size() - 1);

            new DownloadArtistImageAsyncTask((ImageView) getActivity()
                    .findViewById(R.id.play_track_artist_image_view))
                    .execute(image.getText());
        }
    }

    /**
     * Updates image for ImageView PlayButton.
     */
    private void updatePlayButton() {
        if (AppData.isSongPlayed) {
            mPlaySongImageView.setImageResource(R.mipmap.ic_pause);
        } else {
            mPlaySongImageView.setImageResource(R.mipmap.ic_play);
        }
    }

    /**
     * This method handles SeekBar in real time mode.
     */
    private Runnable runnable = new Runnable() {
        public void run() {
            int progress = AppData.audioPlayer.getMediaPlayer().getCurrentPosition();
            mTimelineSeekBar.setProgress(progress);
            mHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mSongCurrentTimeTextView.setText(String.valueOf(progress / 1000));

        if (fromUser) {
            AppData.audioPlayer.getMediaPlayer().seekTo(progress);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
