package com.kpfu.itis.culturalchallenge.api.geocode;

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

    private static final String API_KEY = "AIzaSyCxLZq0WA8jk3fuBlOFfrqVe79ZqHOGUXQ";
    private static final String BASE_URL = "http://maps.googleapis.com/maps/api/geocode/";
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
        Observable<GeocodeResponse> geocodeResponseObservable
                =mGeocodeRequest.getGeocode(request,API_KEY);
        return geocodeResponseObservable.flatMap(
                geocodeResponse -> Observable.<Location>create(e -> {
                    if(geocodeResponse.getStatus().equals("OK")){
                        if(geocodeResponse.getResults().isEmpty()){
                            Location location=new Location();
                            location.setExist(false);
                            e.onNext(location);
                        }else {
                            for (Result result : geocodeResponse.getResults()) {
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

    public Observable<Location> getCoordinatesByAddress(GeocodeRequestBuilder request){
        return getCoordinatesByAddress(request.build());
    }

}
