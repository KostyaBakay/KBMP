package com.kostyabakay.kbmp.util;

import com.kostyabakay.kbmp.audio.AudioPlayer;

/**
 * Created by Kostya on 31.03.2016.
 * This class represents some important application data.
 */
public class AppData {
    private static AppData mAppData = new AppData();
    public static AudioPlayer audioPlayer = new AudioPlayer();
    public static boolean isSongPlayed;
    public static String previousSongPath;
    public static String currentSongPath;
    public static String nextSongPath;
    public static String songUrl;
    public static int selectedNavigationDrawerItem = 0;
    public static int playingTrackMode = 0;

    public static AppData getInstance() {
        return mAppData;
    }

    public AppData() {
        mAppData = this;
    }
}
