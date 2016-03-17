package com.kostyabakay.kbmp.asynctask;

import android.os.AsyncTask;

import com.kostyabakay.kbmp.model.chart.top.tracks.Attr;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;
import com.kostyabakay.kbmp.model.chart.top.tracks.Tracks;
import com.kostyabakay.kbmp.model.chart.top.tracks.TracksResponse;
import com.kostyabakay.kbmp.retrofit.LastFmService;
import com.kostyabakay.kbmp.Constants;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Kostya on 16.03.2016.
 */
public class GetTopTracksAsyncTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.LAST_FM_BASE_URL) // setServer() is deprecated
                .build();

        LastFmService lastFmApi = restAdapter.create(LastFmService.class);

        try {
            TracksResponse response = lastFmApi.getTopTracks();
            System.out.println("Tracks: " + response.toString());

            Tracks tracksResponse = response.getTracksResponse();
            List<Track> tracksList = tracksResponse.getTrack();
            Attr attr = tracksResponse.getAttr();

            for (Track track : tracksList) {
                System.out.println("Track: " + track.getName());
            }

        } catch (RetrofitError error) {
            if (error.getResponse() != null) {
                int code = error.getResponse().getStatus();
                System.out.println("Http error, status : " + code);
            } else {
                System.out.println("Unknown error");
                error.printStackTrace();
            }
        }

        return null;
    }
}
