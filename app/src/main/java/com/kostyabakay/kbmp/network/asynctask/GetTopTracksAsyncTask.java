package com.kostyabakay.kbmp.network.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

import com.kostyabakay.kbmp.util.Constants;
import com.kostyabakay.kbmp.model.chart.top.tracks.Attr;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;
import com.kostyabakay.kbmp.model.chart.top.tracks.Tracks;
import com.kostyabakay.kbmp.model.chart.top.tracks.TracksResponse;
import com.kostyabakay.kbmp.network.retrofit.LastFmService;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Kostya on 16.03.2016.
 * This AsyncTask uses GET method for Chart.getTopTracks query.
 */
public class GetTopTracksAsyncTask extends AsyncTask<Void, Void, ArrayList<Track>> {
    private Activity mActivity;
    public ArrayList<Track> mTracks;

    public GetTopTracksAsyncTask(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.LAST_FM_BASE_URL) // setServer() is deprecated
                .build();

        LastFmService lastFmApi = restAdapter.create(LastFmService.class);
        mTracks = new ArrayList<>();

        try {
            TracksResponse response = lastFmApi.getTopTracks();
            System.out.println("Tracks: " + response.toString());

            Tracks tracksResponse = response.getTracksResponse();
            List<Track> tracksList = tracksResponse.getTrack();
            Attr attr = tracksResponse.getAttr();

            int id = 1;
            for (Track track : tracksList) {
                System.out.println("Track: " + track.getName());
                track.setMbid(Integer.toString(id));
                mTracks.add(track);
                id++;
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

        return mTracks;
    }

    @Override
    protected void onPostExecute(ArrayList<Track> tracks) {
        super.onPostExecute(tracks);
        getTopTracks();
    }

    public ArrayList<Track> getTopTracks() {
        return mTracks;
    }
}
