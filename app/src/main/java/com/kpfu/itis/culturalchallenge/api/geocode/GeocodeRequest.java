package com.kpfu.itis.culturalchallenge.api.geocode;

import com.kpfu.itis.culturalchallenge.api.geocode.pojo.GeocodeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rage on 18.07.2017.
 */

public interface GeocodeRequest {

    @GET("json")
    Observable<GeocodeResponse> getGeocode(
            @Query("address") String address,
            @Query("key") String key);

}
