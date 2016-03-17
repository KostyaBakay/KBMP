package com.kostyabakay.kbmp.model.chart.top.tracks;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 14.03.2016.
 */
public class Tracks {

    @SerializedName("track")
    @Expose
    private List<Track> trackList = new ArrayList<Track>();
    @SerializedName("@attr")
    @Expose
    private com.kostyabakay.kbmp.model.chart.top.tracks.Attr Attr;

    /**
     * @return The track
     */
    public List<Track> getTrack() {
        return trackList;
    }

    /**
     * @param track The track
     */
    public void setTrack(List<Track> track) {
        this.trackList = track;
    }

    /**
     * @return The Attr
     */
    public com.kostyabakay.kbmp.model.chart.top.tracks.Attr getAttr() {
        return Attr;
    }

    /**
     * @param Attr The @attr
     */
    public void setAttr(com.kostyabakay.kbmp.model.chart.top.tracks.Attr Attr) {
        this.Attr = Attr;
    }

}
