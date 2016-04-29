package com.kostyabakay.kbmp.model.artist.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya on 29.04.2016.
 */
public class Tags {

    @SerializedName("tag")
    @Expose
    private List<Tag> tag = new ArrayList<Tag>();

    /**
     * @return The tag
     */
    public List<Tag> getTag() {
        return tag;
    }

    /**
     * @param tag The tag
     */
    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

}
