package com.kostyabakay.kbmp.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.adapter.ViewPagerAdapter;
import com.kostyabakay.kbmp.asynctask.GetJournalAsyncTask;
import com.kostyabakay.kbmp.fragment.VKAuthorizationFragment;
import com.vk.sdk.util.VKUtil;

import java.util.Arrays;

/**
 * Created by Kostya on 09.03.2016.
 * This class represents the main Activity for application.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private VKAuthorizationFragment mVkAuthorizationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.class.getSimpleName(), "onCreate");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        startVkComponents();

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPagerAdapter.updateViewPagerAdapter(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        GetJournalAsyncTask getJournalAsyncTask = new GetJournalAsyncTask();
        getJournalAsyncTask.execute();
    }

    @Override
    public void onBackPressed() {
        Log.d(MainActivity.class.getSimpleName(), "onBackPressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(MainActivity.class.getSimpleName(), "onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(MainActivity.class.getSimpleName(), "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d(MainActivity.class.getSimpleName(), "onNavigationItemSelected");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (id == R.id.vk_authorization) {
            ft.replace(R.id.container, mVkAuthorizationFragment);
        } else if (id == R.id.playlist) {
            mViewPager.setCurrentItem(0);
        } else if (id == R.id.play_track) {
            mViewPager.setCurrentItem(1);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ViewPagerAdapter getViewPagerAdapter() {
        Log.d(MainActivity.class.getSimpleName(), "getViewPagerAdapter");
        return mViewPagerAdapter;
    }

    public ViewPager getViewPager() {
        Log.d(MainActivity.class.getSimpleName(), "getViewPager");
        return mViewPager;
    }

    private void startVkComponents() {
        getFingerPrints();
        mVkAuthorizationFragment = new VKAuthorizationFragment();
    }

    private void getFingerPrints() {
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        System.out.println("Fingerprints: " + Arrays.asList(fingerprints));
    }
}
