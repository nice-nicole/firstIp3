package com.example.firstip3.network;

import com.example.firstip3.models.YelpBusinessesSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpApi {
    @GET("businesses/search")
    Call<YelpBusinessesSearchResponse> getNews(
            @Query("location") String location,
            @Query("term") String term
    );
}