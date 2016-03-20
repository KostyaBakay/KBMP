package com.kostyabakay.kbmp.audio;

import android.content.Context;
import android.media.MediaPlayer;

import com.kostyabakay.kbmp.R;

/**
 * Created by Kostya on 20.03.2016.
 */
public class AudioPlayer {
    private MediaPlayer mMediaPlayer;

    public void play(Context c) {
        stop();
        mMediaPlayer = MediaPlayer.create(c, R.raw.one_small_step);
        mMediaPlayer.start();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
