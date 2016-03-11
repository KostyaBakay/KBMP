package com.kostyabakay.kbmp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kostyabakay.kbmp.fragment.PlayTrackFragment;
import com.kostyabakay.kbmp.fragment.PlaylistFragment;

/**
 * Created by Kostya on 10.03.2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return PlaylistFragment.newInstance();
            case 1:
                return PlayTrackFragment.newInstance();
            default:
                return PlaylistFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
