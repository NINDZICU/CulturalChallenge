package com.kpfu.itis.culturalchallenge.api.geocode;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kpfu.itis.culturalchallenge.api.geocode.pojo.GeocodeResponse;
import com.kpfu.itis.culturalchallenge.api.geocode.pojo.Location;
import com.kpfu.itis.culturalchallenge.api.geocode.pojo.Result;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rage on 18.07.2017.
 */

public class GeocodeApi {

    private static final String COUNTRY=" Россия";
    private static final String API_KEY = "AIzaSyBeKQHjAxJcITvnJK0Tg5Pr9iLkEJnp_4I";
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/";
    private GeocodeRequest mGeocodeRequest;

    public GeocodeApi() {
        mGeocodeRequest = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(GeocodeRequest.class);

    }

    public Observable<Location> getCoordinatesByAddress(String request){
        request+=COUNTRY;
        request=request.trim().replaceAll(" ","+");
        Observable<GeocodeResponse> geocodeResponseObservable
                =mGeocodeRequest.getGeocode(request,API_KEY);
        return geocodeResponseObservable.flatMap(
                geocodeResponse -> Observable.<Location>create(e -> {
                    Log.v("STTT",geocodeResponse.getStatus());
                    if(geocodeResponse.getStatus().equals("OK")){
                        if(geocodeResponse.getResults().isEmpty()){
                            Location location=new Location();
                            location.setExist(false);
                            e.onNext(location);
                        }else {
                            for (Result result : geocodeResponse.getResults()) {
                                Log.v("STTT",result.getFormattedAddress());
                                e.onNext(result.getGeometry().getLocation());
                            }
                        }
                        e.onComplete();
                    }else{
                        Location location=new Location();
                        location.setExist(false);
                        e.onNext(location);
                    }
                })
        );
    }

}
