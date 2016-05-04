package com.kostyabakay.kbmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.activity.MainActivity;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

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
    public void onResume() {
        super.onStart();
        Log.d(VkAuthorizationFragment.class.getSimpleName(), "onResume");
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(getActivity(), scope);
        } else {
            Toast.makeText(getActivity(), R.string.you_are_logged, Toast.LENGTH_LONG).show();
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.setUserAvatar();
            mainActivity.setUserName();
        }
    }
}
