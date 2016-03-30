package com.kostyabakay.kbmp.asynctask;

import android.os.AsyncTask;

import com.kostyabakay.kbmp.retrofit.JournalService;
import com.kostyabakay.kbmp.model.Journal;
import com.kostyabakay.kbmp.util.JournalURL;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Kostya on 12.03.2016.
 */
public class GetJournalAsyncTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(JournalURL.BASE_URL) // setServer() is deprecated
                .build();

        JournalService journalApi = restAdapter.create(JournalService.class);

        try {
            for (JournalURL url : JournalURL.values()) {
                Journal journal = journalApi.getJournalInfo(url.getUrl());
                System.out.println("Journal: " + journal.getTitle());
                System.out.println("Description: " + journal.getDescription());
            }
        } catch (RetrofitError error) {
            if (error.getResponse() != null) {
                int code = error.getResponse().getStatus();
                System.out.println("Http error, status : " + code);
            } else {
                System.out.println("Unknown error");
                error.printStackTrace();
            }
        }

        return null;
    }
}
