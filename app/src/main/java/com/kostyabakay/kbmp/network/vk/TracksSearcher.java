package com.kostyabakay.kbmp.network.vk;

import android.content.Context;
import android.util.Log;

import com.kostyabakay.kbmp.model.VkTrack;
import com.kostyabakay.kbmp.util.AppData;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;

/**
 * Created by Kostya on 30.04.2016.
 * This class contains method which finds and starts playing corresponding song from vk.com.
 */
public class TracksSearcher {
    private Context mContext;

    public TracksSearcher(Context context) {
        this.mContext = context;
    }

    public void searchTrack(String trackName) {
        VKRequest searchSongRequest =
                new VKRequest("audio.search", VKParameters.from(VKApiConst.Q, trackName));
        searchSongRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                ArrayList<VkTrack> trackList = VkTrack.parseJSON(response.responseString);
                String song;

                if (trackList != null) {
                    song = trackList.get(0).getArtist() + " - " + trackList.get(0).getTitle();
                    AppData.sSongUrl = trackList.get(0).getUrl();
                    Log.d(TracksSearcher.class.getSimpleName(), song);
                    Log.d(TracksSearcher.class.getSimpleName(), AppData.sSongUrl);
                } else {
                    Log.e(TracksSearcher.class.getSimpleName(), "trackList is null");
                }

                AppData.sAudioPlayer.play(mContext, AppData.sSongUrl);
                AppData.isSongPlayed = true;
            }
        });
    }
}
