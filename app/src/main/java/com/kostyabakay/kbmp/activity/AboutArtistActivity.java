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
import android.widget.TextView;

import com.kostyabakay.kbmp.R;

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
        TextView textView = (TextView) findViewById(R.id.about_artist_name);
        Intent intent = getIntent();
        String artistName = intent.getStringExtra("artist_name");
        textView.setText(artistName);
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
