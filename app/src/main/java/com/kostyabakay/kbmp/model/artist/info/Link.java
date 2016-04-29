package com.kostyabakay.kbmp.model.artist.info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kostya on 29.04.2016.
 */
public class Link {

    @SerializedName("#text")
    @Expose
    private String Text;
    @SerializedName("rel")
    @Expose
    private String rel;
    @SerializedName("href")
    @Expose
    private String href;

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
     * @return The rel
     */
    public String getRel() {
        return rel;
    }

    /**
     * @param rel The rel
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * @return The href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href The href
     */
    public void setHref(String href) {
        this.href = href;
    }

}
