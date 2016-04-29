package com.kostyabakay.kbmp.model.artist.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 29.04.2016.
 */
public class Links {

    @SerializedName("link")
    @Expose
    private Link link;

    /**
     * @return The link
     */
    public Link getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(Link link) {
        this.link = link;
    }

}
