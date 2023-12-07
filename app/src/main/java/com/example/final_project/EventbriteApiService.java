package com.example.final_project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface EventbriteApiService {

    @GET("events/search")
    Call<EventResponse> searchEvents(
            @Header("Authorization") String authToken,
            @Query("q") String query,
            @Query("location.latitude") double latitude,
            @Query("location.longitude") double longitude,
            @Query("location.distance") String distance
    );
}
