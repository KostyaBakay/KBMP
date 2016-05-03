package com.kostyabakay.kbmp.network.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.kostyabakay.kbmp.activity.MainActivity;
import com.kostyabakay.kbmp.model.artist.info.ArtistInfo;
import com.kostyabakay.kbmp.model.artist.info.ArtistResponse;
import com.kostyabakay.kbmp.model.artist.info.Image;
import com.kostyabakay.kbmp.network.retrofit.LastFmService;
import com.kostyabakay.kbmp.util.Constants;

import java.io.InputStream;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Kostya on 08.04.2016.
 * This AsyncTask takes ImageView id and artist name, downloads image using name and sets this image
 * in ImageView.
 */
public class DownloadLocalArtistImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private MainActivity mMainActivity;
    private ImageView mArtistImageView;

    public DownloadLocalArtistImageAsyncTask(MainActivity mainActivity, ImageView imageView) {
        this.mMainActivity = mainActivity;
        this.mArtistImageView = imageView;
    }

    protected Bitmap doInBackground(String... name) {
        if (mMainActivity.isNetworkConnected()) {
            String artistName = name[0];

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(Constants.LAST_FM_BASE_URL)
                    .build();
            try {
                LastFmService lastFmApi = restAdapter.create(LastFmService.class);
                ArtistResponse response = lastFmApi.getArtist(artistName);
                ArtistInfo artistInfo = response.getArtist();

                List<Image> images = artistInfo.getImage();
                // images.size() = 6
                // images.size() - 6 => small size of image
                // images.size() - 5 => medium size of image
                // images.size() - 4 => large size of image
                // images.size() - 3 => extra large size of image, best for phone!
                // images.size() - 2 => mega size of image, really big size
                // images.size() - 1 => last size of image is nearly medium or small. WTF?
                String artistImageUrl = images.get(images.size() - 3).getText();
                InputStream in = new java.net.URL(artistImageUrl).openStream();
                return BitmapFactory.decodeStream(in);
            } catch (RetrofitError error) {
                if (error.getResponse() != null) {
                    int code = error.getResponse().getStatus();
                    Log.e(ArtistGetInfoAsyncTask.class.getSimpleName(), "Http error, status: " + code);
                } else {
                    Log.e(ArtistGetInfoAsyncTask.class.getSimpleName(), "Unknown error");
                    error.printStackTrace();
                }
                return null;
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    protected void onPostExecute(Bitmap result) {
        if (mMainActivity.isNetworkConnected()) {
            mArtistImageView.setImageBitmap(result);
        }
    }
}

