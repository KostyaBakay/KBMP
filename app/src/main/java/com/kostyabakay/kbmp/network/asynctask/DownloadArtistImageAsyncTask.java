package com.kostyabakay.kbmp.network.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Kostya on 08.04.2016.
 * This AsyncTask takes ImageView id and artist image URL, downloads this image and sets this image
 * in ImageView.
 */
public class DownloadArtistImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView mArtistImageView;

    public DownloadArtistImageAsyncTask(ImageView imageView) {
        this.mArtistImageView = imageView;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bitmap = null;

        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        mArtistImageView.setImageBitmap(result);
    }
}
