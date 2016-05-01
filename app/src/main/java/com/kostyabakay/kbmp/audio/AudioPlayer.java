package com.kostyabakay.kbmp.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.kostyabakay.kbmp.adapter.ViewPagerAdapter;
import com.kostyabakay.kbmp.fragment.PlayTrackFragment;
import com.kostyabakay.kbmp.util.AppData;

import java.io.IOException;

/**
 * Created by Kostya on 20.03.2016.
 * This class works with audio.
 */
public class AudioPlayer {
    private ViewPagerAdapter mViewPagerAdapter;
    private MediaPlayer mMediaPlayer = new MediaPlayer();

    /**
     * Plays audio file from some URL or local path.
     *
     * @param context
     * @param source
     */
    public void play(Context context, String source) {
        stop();
        mMediaPlayer = getMediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            if (source != null) {
                Uri songUri = Uri.parse(source);
                mMediaPlayer.setDataSource(context, songUri);
                mMediaPlayer.prepare(); // Might take long! (for buffering, etc)
                PlayTrackFragment fragment = (PlayTrackFragment) mViewPagerAdapter.getPlayTrackFragment();
                fragment.listenSeekBar();
                AppData.sTrackDuration = mMediaPlayer.getDuration();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            Log.e(AudioPlayer.class.getSimpleName(), "IOException of MediaPlayer");
        }

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });
    }

    /**
     * Resumes playing audio file.
     */
    public void resume() {
        mMediaPlayer.start();
    }

    /**
     * Makes pause of playing audio file.
     */
    public void pause() {
        mMediaPlayer.pause();
    }

    /**
     * Stops playing audio file.
     */
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public MediaPlayer getMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }

        return mMediaPlayer;
    }

    public void setViewPagerAdapter(ViewPagerAdapter adapter) {
        this.mViewPagerAdapter = adapter;
    }
}
