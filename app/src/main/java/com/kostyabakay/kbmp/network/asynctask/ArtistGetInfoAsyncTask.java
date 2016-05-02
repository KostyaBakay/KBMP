package com.kostyabakay.kbmp.network.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.model.artist.info.ArtistInfo;
import com.kostyabakay.kbmp.model.artist.info.ArtistResponse;
import com.kostyabakay.kbmp.model.artist.info.Bio;
import com.kostyabakay.kbmp.model.artist.info.Image;
import com.kostyabakay.kbmp.model.artist.info.Tag;
import com.kostyabakay.kbmp.model.artist.info.Tags;
import com.kostyabakay.kbmp.network.retrofit.LastFmService;
import com.kostyabakay.kbmp.util.Constants;

import java.io.InputStream;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Kostya on 30.04.2016.
 * This AsyncTask downloads information about artist.
 */
public class ArtistGetInfoAsyncTask extends AsyncTask<String, Void, ArtistInfo> {
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private ImageView mArtistImage;
    private Bitmap mArtistImageBitmap;
    private TextView mArtistName;
    private TextView mPlayCount;
    private TextView mListeners;
    private TextView mFirstTag;
    private TextView mSecondTag;
    private TextView mOnTour;
    private TextView mBio;

    public ArtistGetInfoAsyncTask(Activity activity, TextView artistName, TextView playCount,
                                  TextView listeners, TextView firstTag, TextView secondTag,
                                  TextView onTour, ImageView artistImage, TextView bio) {
        this.mActivity = activity;
        this.mArtistName = artistName;
        this.mPlayCount = playCount;
        this.mListeners = listeners;
        this.mFirstTag = firstTag;
        this.mSecondTag = secondTag;
        this.mOnTour = onTour;
        this.mArtistImage = artistImage;
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
            mArtistImageBitmap = BitmapFactory.decodeStream(in);

            return artistInfo;
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

    @Override
    protected void onPostExecute(ArtistInfo artistInfo) {
        super.onPostExecute(artistInfo);
        mArtistName.setText(artistInfo.getName());
        mPlayCount.setText(artistInfo.getStats().getPlaycount());
        mListeners.setText(artistInfo.getStats().getListeners());

        // Tags
        Tags tagsList = artistInfo.getTags();
        List<Tag> tags = tagsList.getTag(); // 5 tags
        if (tags.get(0).getName() != null) mFirstTag.setText(tags.get(0).getName());
        if (tags.get(1).getName() != null) mSecondTag.setText(tags.get(1).getName());

        // On tour
        boolean onTour = false;
        String onTourStr = artistInfo.getOntour(); // 1 => yes and 0 => no
        if (onTourStr.contains("1")) onTour = true;
        else if (onTourStr.contains("0")) onTour = false;
        if (onTour) {
            mOnTour.setText(R.string.yes);
        } else {
            mOnTour.setText(R.string.no);
        }

        mArtistImage.setImageBitmap(mArtistImageBitmap);
        Bio bio = artistInfo.getBio();
        mBio.setText(bio.getContent());
        mProgressDialog.dismiss();
    }
}

