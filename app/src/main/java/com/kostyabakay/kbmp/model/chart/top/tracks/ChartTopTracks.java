package com.kostyabakay.kbmp.model.chart.top.tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 14.03.2016.
 */
public class ChartTopTracks {

    @SerializedName("tracks")
    @Expose
    private Tracks tracks;

    /**
     * @return The tracks
     */
    public Tracks getTracks() {
        return tracks;
    }

    /**
     * @param tracks The tracks
     */
    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

}
