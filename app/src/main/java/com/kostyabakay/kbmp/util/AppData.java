package com.kostyabakay.kbmp.util;

import android.content.Context;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.audio.AudioPlayer;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * Created by Kostya on 31.03.2016.
 * This class represents some important application data.
 */
public class AppData {
    private static AppData sAppData = new AppData();
    public static AudioPlayer sAudioPlayer = new AudioPlayer();
    public static boolean isSongPlayed;
    public static String[] sAudioPath;
    public static int sTrackPosition = 0;
    public static int sTrackDuration = 0;
    public static String sPreviousSongPath;
    public static String sCurrentSongPath;
    public static String sNextSongPath;
    public static String sSongUrl;
    public static int sSelectedNavigationDrawerItem = 0;
    public static int sPlayingTrackMode = Constants.NOT_ACTIVE_PLAYING_TRACK_MODE;
    public static String artistName;
    public static String trackName;

    public static AppData getInstance() {
        return sAppData;
    }

    public AppData() {
        sAppData = this;
    }

    public static ImageLoaderConfiguration getImageLoaderConfig(Context context) {
        L.writeLogs(false);
        return new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .writeDebugLogs()
                .defaultDisplayImageOptions(getDefaultImageOption())
                .diskCache(new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context, true)))
                .build();
    }

    private static DisplayImageOptions getDefaultImageOption() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.last_fm_logo)
                .build();
    }
}
