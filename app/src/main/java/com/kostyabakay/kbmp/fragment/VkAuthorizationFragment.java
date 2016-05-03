package com.kostyabakay.kbmp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.activity.MainActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by Kostya on 20.03.2016.
 * This is fragment for vk.com authorization.
 */
public class VkAuthorizationFragment extends Fragment {
    private String[] scope = new String[]{
            VKScope.AUDIO,
            VKScope.MESSAGES,
            VKScope.FRIENDS,
            VKScope.WALL
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(VkAuthorizationFragment.class.getSimpleName(), "onCreateView");
        return inflater.inflate(R.layout.fragment_vk_authorization, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(VkAuthorizationFragment.class.getSimpleName(), "onStart");
        VKSdk.login(getActivity(), scope);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(VkAuthorizationFragment.class.getSimpleName(), "onActivityResult");
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {

            @Override
            public void onResult(VKAccessToken res) {
                Log.d(VkAuthorizationFragment.class.getSimpleName(),
                        "onResult: User successfully logged");
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setUserAvatar();
                mainActivity.setUserName();
            }

            @Override
            public void onError(VKError error) {
                Log.d(VkAuthorizationFragment.class.getSimpleName(),
                        "onResult: An authorization error");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
