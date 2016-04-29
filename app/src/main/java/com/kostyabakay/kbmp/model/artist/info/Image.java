package com.kostyabakay.kbmp.model.artist.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 29.04.2016.
 */
public class Image {

    @SerializedName("#text")
    @Expose
    private String Text;
    @SerializedName("size")
    @Expose
    private String size;

    /**
     * @return The Text
     */
    public String getText() {
        return Text;
    }

    /**
     * @param Text The #text
     */
    public void setText(String Text) {
        this.Text = Text;
    }

    /**
     * @return The size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size The size
     */
    public void setSize(String size) {
        this.size = size;
    }

}
