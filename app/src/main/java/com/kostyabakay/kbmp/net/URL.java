package com.kostyabakay.kbmp.net;

/**
 * Created by Kostya on 12.03.2016.
 */
public enum URL {
    ECONOMICS("economics.json"),
    PHYSICS("mathematical.json"),
    MATHEMATICAL("physics.json");

    public static final String BASE_URL = "https://dl.dropboxusercontent.com/u/17192683/rest/journals/";

    private String url;
    URL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
