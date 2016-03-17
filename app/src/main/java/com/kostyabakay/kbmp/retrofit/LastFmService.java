package com.kostyabakay.kbmp.retrofit;

import com.kostyabakay.kbmp.model.chart.top.tracks.TracksResponse;
import com.kostyabakay.kbmp.Constants;

import retrofit.http.GET;

/**
 * Created by Kostya on 16.03.2016.
 */
public interface LastFmService {
    @GET("/?method=chart.gettoptracks&api_key="+ Constants.LAST_FM_API_KEY +"&format=json")
    TracksResponse getTopTracks();
}
