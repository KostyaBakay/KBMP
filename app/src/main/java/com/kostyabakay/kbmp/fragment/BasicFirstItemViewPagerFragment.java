package com.kostyabakay.kbmp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.util.AppData;

/**
 * Created by Kostya on 10.04.2016.
 * This class represents container for fragments at first item of ViewPager.
 */
public class BasicFirstItemViewPagerFragment extends Fragment {

    public static BasicFirstItemViewPagerFragment newInstance() {
        BasicFirstItemViewPagerFragment fragment = new BasicFirstItemViewPagerFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basic_first_item_of_view_pager, container, false);
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (AppData.selectedNavigationDrawerItem == 0) {
            // When this container fragment is created, we fill it with our first "real" fragment
            ft.replace(R.id.fragment_basic_first_item_of_view_pager, new PlaylistFragment());
            ft.commit();
        }

        return view;
    }
}
