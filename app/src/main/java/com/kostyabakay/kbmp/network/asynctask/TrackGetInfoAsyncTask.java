package com.kostyabakay.kbmp.network.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.model.track.info.Tag;
import com.kostyabakay.kbmp.model.track.info.Tags;
import com.kostyabakay.kbmp.model.track.info.Track;
import com.kostyabakay.kbmp.model.track.info.TrackResponse;
import com.kostyabakay.kbmp.network.retrofit.LastFmService;
import com.kostyabakay.kbmp.util.Constants;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Kostya on 02.05.2016.
 * This AsyncTask downloads information about track.
 */
public class TrackGetInfoAsyncTask extends AsyncTask<String, Void, Track> {
    private Activity mActivity;
    private ProgressDialog mProgressDialog;
    private TextView mArtistName;
    private TextView mTrackName;
    private TextView mPlayCount;
    private TextView mListeners;
    private TextView mTagHeadline;
    private TextView mFirstTag;
    private TextView mSecondTag;
    private TextView mSummaryHeadline;
    private TextView mSummary;

    public TrackGetInfoAsyncTask(Activity activity, TextView artistName, TextView trackName,
                                 TextView playCount, TextView listeners, TextView tagHeadline,
                                 TextView firstTag, TextView secondTag, TextView summaryHeadline,
                                 TextView summary) {
        this.mActivity = activity;
        this.mArtistName = artistName;
        this.mTrackName = trackName;
        this.mPlayCount = playCount;
        this.mListeners = listeners;
        this.mTagHeadline = tagHeadline;
        this.mFirstTag = firstTag;
        this.mSecondTag = secondTag;
        this.mSummaryHeadline = summaryHeadline;
        this.mSummary = summary;
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
    protected Track doInBackground(String... params) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.LAST_FM_BASE_URL)
                .build();
        try {
            LastFmService lastFmApi = restAdapter.create(LastFmService.class);
            TrackResponse response = lastFmApi.getTrack(params[0], params[1]);
            return response.getTrack(); // Track instance
        } catch (RetrofitError error) {
            if (error.getResponse() != null) {
                int code = error.getResponse().getStatus();
                Log.e(TrackGetInfoAsyncTask.class.getSimpleName(), "Http error, status: " + code);
            } else {
                Log.e(TrackGetInfoAsyncTask.class.getSimpleName(), "Unknown error");
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
    protected void onPostExecute(Track track) {
        super.onPostExecute(track);
        mArtistName.setText(track.getArtist().getName());
        mTrackName.setText(track.getName());
        mPlayCount.setText(track.getPlaycount());
        mListeners.setText(track.getListeners());

        // Tags
        Tags topTags = track.getToptags();
        List<Tag> tags = topTags.getTag(); // 5 tags
        if (tags.size() > 0) {
            mTagHeadline.setText(R.string.tags);
            if (tags.size() > 0 && tags.get(0).getName() != null) {
                mFirstTag.setText(tags.get(0).getName());
            }
            if (tags.size() > 1 && tags.get(1).getName() != null) {
                mSecondTag.setText(tags.get(1).getName());
            }
        }

        // Summary
        if (track.getWiki() != null && !track.getWiki().getContent().equals("")) {
            mSummaryHeadline.setText(R.string.summary);
            mSummary.setText(track.getWiki().getContent());
        }

        mProgressDialog.dismiss();
    }
}

