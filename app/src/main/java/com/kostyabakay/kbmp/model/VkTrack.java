package com.kostyabakay.kbmp.model;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kostya on 29.03.2016.
 * This class represents track model from vk.com and implements simple JSON parser.
 */
public class VkTrack {
    private long mId;
    private long mOwnerId;
    private String mArtist;
    private String mTitle;
    private int mDuration;
    private long mDate;
    private String mUrl;
    private long mLyricsId;
    private int mGenreId;

    @NonNull
    public static ArrayList<VkTrack> parseArray(JSONArray jsonArray) {
        ArrayList<VkTrack> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                VkTrack track = parse(jsonArray.getJSONObject(i));
                list.add(track);
            } catch (JSONException e) {
                Log.e(VkTrack.class.getSimpleName(), "JSONException in parseArray method");
            }
        }

        return list;
    }

    @NonNull
    public static VkTrack parse(JSONObject jsonObject) {
        VkTrack object = new VkTrack();

        try {
            // getLong gets throwable exception
            if (!jsonObject.isNull("id")) object.mId = jsonObject.getLong("id");
            if (!jsonObject.isNull("owner_id")) object.mOwnerId = jsonObject.getLong("owner_id");
            if (!jsonObject.isNull("artist")) object.mArtist = jsonObject.getString("artist");
            if (!jsonObject.isNull("title")) object.mTitle = jsonObject.getString("title");
            if (!jsonObject.isNull("duration")) object.mDuration = jsonObject.getInt("duration");
            if (!jsonObject.isNull("date")) object.mDate = jsonObject.getLong("date");
            if (!jsonObject.isNull("url")) object.mUrl = jsonObject.getString("url");
            if (!jsonObject.isNull("lyrics_id")) object.mLyricsId = jsonObject.getLong("lyrics_id");
            if (!jsonObject.isNull("genre_id")) object.mGenreId = jsonObject.getInt("genre_id");

            // optLong returns default value, but slow performance
            // if (!jsonObject.isNull("id")) object.mId = jsonObject.optLong("id");
        } catch (JSONException e) {
            Log.e(VkTrack.class.getSimpleName(), "JSONException in parse method");
        }

        return object;
    }

    public static ArrayList<VkTrack> parseJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json).getJSONObject("response");
            ArrayList<VkTrack> list = VkTrack.parseArray(jsonObject.getJSONArray("items"));
            return list;
        } catch (JSONException e) {
            Log.e(VkTrack.class.getSimpleName(), "JSONException in parseJSON method");
        }

        return null;
    }

    public String getArtist() {
        return mArtist;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
