package com.kostyabakay.kbmp.model.track.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 02.05.2016.
 */
public class TrackResponse {

    @SerializedName("track")
    @Expose
    private Track track;

    /**
     * @return The track
     */
    public Track getTrack() {
        return track;
    }

    /**
     * @param track The track
     */
    public void setTrack(Track track) {
        this.track = track;
    }

}
