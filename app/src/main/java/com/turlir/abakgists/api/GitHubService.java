package com.turlir.abakgists.api;


import com.turlir.abakgists.api.data.GistJson;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface GitHubService {

    @GET("gists/public?per_page=30")
    Observable<List<GistJson>> publicGist(
            @Query(value = "page") int page
    );

}
