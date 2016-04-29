package com.kostyabakay.kbmp.model.artist.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya on 29.04.2016.
 */
public class Similar {

    @SerializedName("artist")
    @Expose
    private List<Artist> artist = new ArrayList<Artist>();

    /**
     * @return The artist
     */
    public List<Artist> getArtist() {
        return artist;
    }

    /**
     * @param artist The artist
     */
    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

}
