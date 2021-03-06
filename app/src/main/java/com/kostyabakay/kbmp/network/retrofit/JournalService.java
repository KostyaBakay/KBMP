package com.kostyabakay.kbmp.network.retrofit;

import com.kostyabakay.kbmp.model.Journal;

import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Kostya on 12.03.2016.
 */
public interface JournalService {
    @POST("/{url}")
    Journal getJournalInfo(@Path("url") String urlJournal);
}
