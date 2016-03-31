package com.kostyabakay.kbmp.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.kostyabakay.kbmp.R;

import java.io.IOException;

/**
 * Created by Kostya on 20.03.2016.
 * This class works with audio.
 */
public class AudioPlayer {
    private MediaPlayer mMediaPlayer;
    private Uri songUri;

    /**
     * Plays local audio file.
     *
     * @param context
     */
    public void play(Context context) {
        stop();
        mMediaPlayer = MediaPlayer.create(context, R.raw.one_small_step);
        mMediaPlayer.start();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });
    }

    /**
     * Plays audio file from some URL.
     *
     * @param context
     * @param url
     */
    public void play(Context context, String url) {
        stop();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            if (url != null) {
                songUri = Uri.parse(url);
                mMediaPlayer.setDataSource(context, songUri);
                mMediaPlayer.prepare(); // Might take long! (for buffering, etc)
                mMediaPlayer.start();
            } else {
                play(context);
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
     * Stops playing audio.
     */
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
