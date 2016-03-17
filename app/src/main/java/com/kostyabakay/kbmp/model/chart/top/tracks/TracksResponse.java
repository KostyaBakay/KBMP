package com.kostyabakay.kbmp.model.chart.top.tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 16.03.2016.
 */
public class TracksResponse {

    @SerializedName("tracks")
    @Expose
    private Tracks tracksResponse;

    public Tracks getTracksResponse() {
        return tracksResponse;
    }

    public void setTracksResponse(Tracks tracks) {
        this.tracksResponse = tracks;
    }
}
