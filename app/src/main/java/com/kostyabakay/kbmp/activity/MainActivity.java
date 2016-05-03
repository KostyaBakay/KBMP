package com.kostyabakay.kbmp.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.adapter.ViewPagerAdapter;
import com.kostyabakay.kbmp.fragment.LocalTracksFragment;
import com.kostyabakay.kbmp.fragment.TopTracksFragment;
import com.kostyabakay.kbmp.fragment.VkAuthorizationFragment;
import com.kostyabakay.kbmp.network.asynctask.GetJournalAsyncTask;
import com.kostyabakay.kbmp.util.AppData;
import com.kostyabakay.kbmp.util.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kostya on 09.03.2016.
 * This class represents the main Activity for application.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private VkAuthorizationFragment mVkAuthorizationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(MainActivity.class.getSimpleName(), "onCreate");
        setupUI();
        initImageLoader();
        startVkComponents();
        listenViewPager();

        // This lines enable simple retrofit example
        GetJournalAsyncTask getJournalAsyncTask = new GetJournalAsyncTask();
        getJournalAsyncTask.execute();
    }

    /**
     * Initialization of view elements.
     */
    private void setupUI() {
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupDrawerLayout();
        setupNavigationView();
        setupViewPager();
    }

    /**
     * Initialization of Toolbar.
     */
    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    /**
     * Initialization of DrawerLayout.
     */
    private void setupDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Initialization of NavigationView.
     */
    private void setupNavigationView() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Initialization of ViewPager.
     */
    private void setupViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        AppData.sAudioPlayer.setViewPagerAdapter(mViewPagerAdapter);
    }

    private void initImageLoader() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
    }

    /**
     * This is listener for the ViewPager.
     */
    private void listenViewPager() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPagerAdapter.updateViewPagerAdapter(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

        if (id == R.id.nav_vk_authorization) {
            if (isNetworkConnected()) {
                ft.replace(R.id.container, mVkAuthorizationFragment);
            } else {
                Toast.makeText(this,
                        R.string.please_check_internet_connection, Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_last_fm_top_tracks) {
            if (isNetworkConnected()) {
                AppData.sSelectedNavigationDrawerItem = Constants.LAST_FM_TOP_TRACKS;
                mViewPager.setCurrentItem(0);
                ft.replace(R.id.fragment_basic_first_item_of_view_pager, new TopTracksFragment());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
            } else {
                Toast.makeText(this,
                        R.string.please_check_internet_connection, Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_local_tracks) {
            AppData.sSelectedNavigationDrawerItem = Constants.USER_LOCAL_TRACKS;
            mViewPager.setCurrentItem(0);
            ft.replace(R.id.fragment_basic_first_item_of_view_pager, new LocalTracksFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
        } else if (id == R.id.nav_developer) {
            showLinkedInAccount();
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

    public boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void startVkComponents() {
        getFingerPrints();
        setUserAvatar();
        setUserName();
        mVkAuthorizationFragment = new VkAuthorizationFragment();
    }

    public void setUserAvatar() {
        VKParameters params = new VKParameters();
        params.put(VKApiConst.FIELDS, "photo_100");
        VKRequest request = new VKRequest("users.get", params);

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONArray resp = response.json.getJSONArray("response");
                    JSONObject user = resp.getJSONObject(0);
                    String url = user.getString("photo_100");
                    CircleImageView circleImageView = (CircleImageView) mNavigationView.getHeaderView(0).
                            findViewById(R.id.user_avatar_navigation_drawer);
                    ImageLoader.getInstance().displayImage(url, circleImageView);
                } catch (JSONException e) {
                    Log.d(MainActivity.class.getSimpleName(), "JSON getting trouble");
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }

    public void setUserName() {
        if (VKSdk.isLoggedIn()) {
            VKApi.users().get().executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    VKApiUser user = ((VKList<VKApiUser>) response.parsedModel).get(0);
                    Log.d(MainActivity.class.getSimpleName(), user.first_name + " " + user.last_name);
                    TextView tv = (TextView) findViewById(R.id.user_name_surname_navigation_drawer);
                    assert tv != null;
                    String fullName = user.first_name + " " + user.last_name;
                    tv.setText(fullName);
                }

                @Override
                public void onError(VKError error) {
                    super.onError(error);
                }
            });
        }
    }

    private void getFingerPrints() {
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        System.out.println("Fingerprints: " + Arrays.asList(fingerprints));
    }

    private void showLinkedInAccount() {
        if (isNetworkConnected()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(Constants.LINKED_IN_URL));
            startActivity(intent);
        } else {
            Toast.makeText(this,
                    R.string.please_check_internet_connection, Toast.LENGTH_LONG).show();
        }
    }
}
