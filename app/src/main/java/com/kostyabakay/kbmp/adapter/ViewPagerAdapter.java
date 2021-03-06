package com.kostyabakay.kbmp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kostyabakay.kbmp.fragment.LocalTracksFragment;
import com.kostyabakay.kbmp.fragment.PlayTrackFragment;
import com.kostyabakay.kbmp.fragment.TopTracksFragment;
import com.kostyabakay.kbmp.fragment.BasicFirstItemViewPagerFragment;
import com.kostyabakay.kbmp.model.chart.top.tracks.Track;
import com.kostyabakay.kbmp.util.AppData;
import com.kostyabakay.kbmp.util.Constants;

import java.util.ArrayList;

/**
 * Created by Kostya on 10.03.2016.
 * This class represents the adapter for ViewPager. This adapter gets page position of the ViewPager
 * and returns created fragment for the corresponding page.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment mBasicFirstItemViewPagerFragment;
    private Fragment mTopTracksFragment;
    private Fragment mLocalTracksFragment;
    private Fragment mPlayTrackFragment;
    private ArrayList<Track> mTracks = new ArrayList<>();
    private Track mPreviousTrack;
    private Track mCurrentTrack;
    private Track mNextTrack;
    private int mCurrentTrackItemIndex;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return getFirstPageViewPager();
            case 1:
                return getPlayTrackFragment();
            default:
                return getFirstPageViewPager();
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

    public Fragment getFirstPageViewPager() {
        if (AppData.sSelectedNavigationDrawerItem == Constants.LAST_FM_TOP_TRACKS) {
            return getTopTracksFragment();
        } else if (AppData.sSelectedNavigationDrawerItem == Constants.USER_LOCAL_TRACKS) {
            return getLocalTracksFragment();
        } else {
            return getBasicFirstItemViewPager();
        }
    }

    public Fragment getBasicFirstItemViewPager() {
        if (mBasicFirstItemViewPagerFragment == null) {
            mBasicFirstItemViewPagerFragment = BasicFirstItemViewPagerFragment.newInstance();
        }
        return mBasicFirstItemViewPagerFragment;
    }

    public Fragment getTopTracksFragment() {
        if (mTopTracksFragment == null) {
            mTopTracksFragment = TopTracksFragment.newInstance();
        }
        return mTopTracksFragment;
    }

    public Fragment getPlayTrackFragment() {
        if (mPlayTrackFragment == null) {
            mPlayTrackFragment = PlayTrackFragment.newInstance();
        }
        return mPlayTrackFragment;
    }

    public Fragment getLocalTracksFragment() {
        if (mLocalTracksFragment == null) {
            mLocalTracksFragment = LocalTracksFragment.newInstance();
        }
        return mLocalTracksFragment;
    }

    public ArrayList<Track> getTracks() {
        return mTracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.mTracks = tracks;
    }

    public int getCurrentTrackItemIndex() {
        return mCurrentTrackItemIndex;
    }

    public void setCurrentTrackItemIndex(int index) {
        this.mCurrentTrackItemIndex = index;
    }

    public Track getPreviousTrack(int index) {
        mPreviousTrack = mTracks.get(index - 1);
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

    public Track getNextTrack(int index) {
        mNextTrack = mTracks.get(index + 1);
        return mNextTrack;
    }

    public void setNextTrack(Track track) {
        this.mNextTrack = track;
    }
}
