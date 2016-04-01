package com.kostyabakay.kbmp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kostyabakay.kbmp.fragment.PlayTrackFragment;
import com.kostyabakay.kbmp.fragment.PlaylistFragment;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the adapter for ViewPager. This adapter gets page position of the ViewPager
 * and returns created fragment for the corresponding page.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment mPlaylistFragment, mPlayTrackFragment;
    private Track mPreviousTrack, mCurrentTrack, mNextTrack;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return getPlaylistFragment();
            case 1:
                return getPlayTrackFragment();
            default:
                return getPlaylistFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void updateViewPagerAdapter(int position) {
        // TODO: Memory leak
        if (position == 1) {
            ((PlayTrackFragment) getItem(1)).updatePlayTrackFragment();
        }
    }

    public Fragment getPlaylistFragment() {
        if (mPlaylistFragment == null) {
            mPlaylistFragment = PlaylistFragment.newInstance();
        }
        return mPlaylistFragment;
    }

    public Fragment getPlayTrackFragment() {
        if (mPlayTrackFragment == null) {
            mPlayTrackFragment = PlayTrackFragment.newInstance();
        }
        return mPlayTrackFragment;
    }

    public Track getPreviousTrack() {
        return mPreviousTrack;
    }

    public void setPreviousTrack(Track track) {
        this.mPreviousTrack = track;
    }

    public Track getCurrentTrack() {
        return mCurrentTrack;
    }

    public void setCurrentTrack(Track track) {
        this.mCurrentTrack = track;
    }

    public Track getNextTrack() {
        return mNextTrack;
    }

    public void setNextTrack(Track track) {
        this.mNextTrack = track;
    }
}
