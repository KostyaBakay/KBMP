package com.kostyabakay.kbmp.model.track.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 02.05.2016.
 */
public class Track {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("streamable")
    @Expose
    private Streamable streamable;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("playcount")
    @Expose
    private String playcount;
    @SerializedName("artist")
    @Expose
    private Artist artist;
    @SerializedName("toptags")
    @Expose
    private Tags toptags;
    @SerializedName("wiki")
    @Expose
    private Wiki wiki;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration The duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return The streamable
     */
    public Streamable getStreamable() {
        return streamable;
    }

    /**
     * @param streamable The streamable
     */
    public void setStreamable(Streamable streamable) {
        this.streamable = streamable;
    }

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

    /**
     * @return The artist
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * @param artist The artist
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * @return The toptags
     */
    public Tags getToptags() {
        return toptags;
    }

    /**
     * @param toptags The toptags
     */
    public void setToptags(Tags toptags) {
        this.toptags = toptags;
    }

    /**
     * @return The wiki
     */
    public Wiki getWiki() {
        return wiki;
    }

    /**
     * @param wiki The wiki
     */
    public void setWiki(Wiki wiki) {
        this.wiki = wiki;
    }

}
