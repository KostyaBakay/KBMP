package com.kostyabakay.kbmp;

import android.content.Intent;
import android.util.Log;

import com.kostyabakay.kbmp.activity.MainActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by Kostya on 20.03.2016.
 * Base class for maintain global application state.
 */
public class Application extends android.app.Application {

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                // VKAccessToken is invalid
                Intent intent = new Intent(Application.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(android.app.Application.class.getSimpleName(), "onCreate");
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }
}
