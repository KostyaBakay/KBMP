package com.kostyabakay.kbmp.network.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kostyabakay.kbmp.model.VkTrack;
import com.kostyabakay.kbmp.util.AppData;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;

/**
 * Created by kostya on 02.04.16.
 * This AsyncTask finds and starts playing corresponding song from vk.com.
 */
public class PlayTrackAsyncTask extends AsyncTask<String, Void, Void> {
    private Context mContext;

    public PlayTrackAsyncTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        VKRequest searchSongRequest =
                new VKRequest("audio.search", VKParameters.from(VKApiConst.Q, params[0]));
        searchSongRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                ArrayList<VkTrack> trackList = VkTrack.parseJSON(response.responseString);
                String song;

                if (trackList != null) {
                    song = trackList.get(0).getArtist() + " - " + trackList.get(0).getTitle();
                    AppData.sSongUrl = trackList.get(0).getUrl();
                    Log.d(PlayTrackAsyncTask.class.getSimpleName(), song);
                    Log.d(PlayTrackAsyncTask.class.getSimpleName(), AppData.sSongUrl);
                } else {
                    Log.e(PlayTrackAsyncTask.class.getSimpleName(), "trackList is null");
                }

                AppData.sAudioPlayer.play(mContext, AppData.sSongUrl);
                AppData.isSongPlayed = true;
            }
        });

        return null;
    }
}
