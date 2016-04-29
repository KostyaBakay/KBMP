package com.kostyabakay.kbmp.model.artist.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 29.04.2016.
 */
public class Stats {

    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("playcount")
    @Expose
    private String playcount;

    /**
     * @return The listeners
     */
    public String getListeners() {
        return listeners;
    }

    /**
     * @param listeners The listeners
     */
    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    /**
     * @return The playcount
     */
    public String getPlaycount() {
        return playcount;
    }

    /**
     * @param playcount The playcount
     */
    public void setPlaycount(String playcount) {
        this.playcount = playcount;
    }

}
