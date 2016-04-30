package com.kostyabakay.kbmp.network.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.kostyabakay.kbmp.model.artist.info.ArtistInfo;
import com.kostyabakay.kbmp.model.artist.info.ArtistResponse;
import com.kostyabakay.kbmp.model.artist.info.Bio;
import com.kostyabakay.kbmp.network.retrofit.LastFmService;
import com.kostyabakay.kbmp.util.Constants;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Kostya on 30.04.2016.
 * This AsyncTask downloads information about artist.
 */
public class ArtistGetInfoAsyncTask extends AsyncTask<String, Void, ArtistInfo> {
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private TextView mArtistName;
    private TextView mBio;

    public ArtistGetInfoAsyncTask(Activity activity, TextView artistName, TextView bio) {
        this.mActivity = activity;
        this.mArtistName = artistName;
        this.mBio = bio;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setTitle("Information about artist");
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    @Override
    protected ArtistInfo doInBackground(String... params) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.LAST_FM_BASE_URL)
                .build();
        try {
            LastFmService lastFmApi = restAdapter.create(LastFmService.class);
            ArtistResponse response = lastFmApi.getArtist(params[0]);
            return response.getArtist(); // ArtistInfo instance
        } catch (RetrofitError error) {
            if (error.getResponse() != null) {
                int code = error.getResponse().getStatus();
                Log.e(ArtistGetInfoAsyncTask.class.getSimpleName(), "Http error, status: " + code);
            } else {
                Log.e(ArtistGetInfoAsyncTask.class.getSimpleName(), "Unknown error");
                error.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArtistInfo artistInfo) {
        super.onPostExecute(artistInfo);
        Bio bio = artistInfo.getBio();
        mArtistName.setText(artistInfo.getName());
        mBio.setText(bio.getContent());
        mProgressDialog.dismiss();
    }
}

