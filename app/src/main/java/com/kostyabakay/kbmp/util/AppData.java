package com.kostyabakay.kbmp.util;

import com.kostyabakay.kbmp.audio.AudioPlayer;

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
    public static int sPlayingTrackMode = 0;
    public static String artistName;

    public static AppData getInstance() {
        return sAppData;
    }

    public AppData() {
        sAppData = this;
    }
}
