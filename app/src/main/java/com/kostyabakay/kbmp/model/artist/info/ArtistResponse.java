package com.kostyabakay.kbmp.model.artist.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 29.04.2016.
 */
public class ArtistResponse {

    @SerializedName("artist")
    @Expose
    private ArtistInfo artist;

    /**
     * @return The artist
     */
    public ArtistInfo getArtist() {
        return artist;
    }

    /**
     * @param artist The artist
     */
    public void setArtist(ArtistInfo artist) {
        this.artist = artist;
    }

}
