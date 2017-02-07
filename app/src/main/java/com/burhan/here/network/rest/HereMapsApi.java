package com.burhan.here.network.rest;

import com.burhan.here.network.model.SearchAroundResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by BURHAN on 2/5/2017.
 */

public interface HereMapsApi {
    /**
     * https://places.cit.api.here.com/places/v1
     * /discover/around?
     * at=41.0073%2C29.2165&cat=cinema&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg
     */

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://places.cit.api.here.com/places/v1/")
            .addConverterFactory(GsonConverterFactory.create())

            .build();

    @GET("discover/around")
    Call<SearchAroundResponse> dicoverAround(@Query("at") String coordinates, @Query("cat") String category,
                                             @Query("app_id") String appId, @Query("app_code") String appCode);
}
