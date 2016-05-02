package com.kostyabakay.kbmp.network.retrofit;

import com.kostyabakay.kbmp.model.artist.info.ArtistResponse;
import com.kostyabakay.kbmp.model.chart.top.tracks.TracksResponse;
import com.kostyabakay.kbmp.model.track.info.TrackResponse;
import com.kostyabakay.kbmp.util.Constants;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Kostya on 16.03.2016.
 */
public interface LastFmService {
    @GET("/?method=chart.gettoptracks&api_key=" + Constants.LAST_FM_API_KEY + "&format=json")
    TracksResponse getTopTracks();

    @GET("/?method=artist.getinfo&api_key=" + Constants.LAST_FM_API_KEY + "&format=json")
    ArtistResponse getArtist(@Query("artist") String artistName);

    @GET("/?method=track.getinfo&api_key=" + Constants.LAST_FM_API_KEY + "&format=json")
    TrackResponse getTrack(@Query("artist") String artistName, @Query("track") String trackName);
}
