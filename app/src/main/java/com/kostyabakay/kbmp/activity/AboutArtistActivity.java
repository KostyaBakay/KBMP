package com.kostyabakay.kbmp.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kostyabakay.kbmp.R;
import com.kostyabakay.kbmp.network.asynctask.ArtistGetInfoAsyncTask;
import com.kostyabakay.kbmp.util.AppData;

/**
 * Created by Kostya on 29.04.2016.
 * This activity represents main information about the artist.
 */
public class AboutArtistActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(AboutArtistActivity.class.getSimpleName(), "onCreate");
        setContentView(R.layout.activity_about_artist);
        setupToolbar();
        Intent intent = getIntent();
        AppData.artistName = intent.getStringExtra("artist_name");

        new ArtistGetInfoAsyncTask(this,
                (TextView) findViewById(R.id.about_artist_name),
                (TextView) findViewById(R.id.about_artist_play_count),
                (TextView) findViewById(R.id.about_artist_listeners),
                (TextView) findViewById(R.id.about_artist_first_tag),
                (TextView) findViewById(R.id.about_artist_second_tag),
                (TextView) findViewById(R.id.about_artist_on_tour),
                (ImageView) findViewById(R.id.about_artist_image),
                (TextView) findViewById(R.id.about_artist_bio_headline),
                (TextView) findViewById(R.id.about_artist_bio))
                .execute(AppData.artistName);
    }

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_about_artist);
        mToolbar.setTitle(R.string.about_artist);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.primary_light));

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(R.color.primary_light), PorterDuff.Mode.SRC_ATOP);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
