package com.kpfu.itis.culturalchallenge.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.kpfu.itis.culturalchallenge.api.geocode.GeocodeApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by Rage on 18.07.2017.
 */

public class ConfirmTask {

    private static final float CONFIRM_DISTANCE_METER=1500;
    private GoogleApiClient mGoogleApiClient;
    private GeocodeApi mGeocodeApi;
    private Context mContext;

    public ConfirmTask(Context context) {
        mContext = context;
        mGeocodeApi = new GeocodeApi();
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .build();
    }

    public Observable<Boolean> exists(String taskAddress){
        return mGeocodeApi.getCoordinatesByAddress(taskAddress).flatMap(location -> Observable.<Boolean>create(e -> {
            try {
                if (!location.isExist()) {
                    e.onNext(false);
                } else {
                    e.onNext(true);
                }
            }catch (Throwable throwable){
                e.onError(throwable);
            }finally {
                e.onComplete();
            }
        })).take(1);
    }

    private boolean checkGps(){
        LocationManager locationManager=(LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);/*{
            throw new Exception("GPS is not enabled!");
        }*/
    }

    public Observable<Boolean> confirm(String taskAddress) throws Exception {
        if(!checkGps()){
            throw new Exception("GPS is not enabled!");
        }
        return mGeocodeApi.getCoordinatesByAddress(taskAddress).flatMap(location -> Observable.<Boolean>create(e -> {
            try {
                if (checkPermission()) {
                    ConnectionResult connectionResult = mGoogleApiClient.blockingConnect(30, TimeUnit.SECONDS);
                    if (connectionResult.isSuccess()) {
                        LocationRequest locationRequest = new LocationRequest();
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationRequest.setNumUpdates(1);
                        locationRequest.setSmallestDisplacement(0);
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,locationRequest,
                                myLocation -> {
                                    float[] dist=new float[1];
                                    Location.distanceBetween(
                                            myLocation.getLatitude(),myLocation.getLongitude(),
                                            location.getLat(),location.getLng(),dist
                                    );
                                    if(dist[0]<CONFIRM_DISTANCE_METER){
                                        e.onNext(true);
                                    }else{
                                        e.onNext(false);
                                    }
                                });
                    } else {
                        e.onError(new Exception("GoogleApiService connect failed!"));
                    }
                } else {
                    e.onError(new Exception("Location permission denied!"));
                }
            }finally {
                if(mGoogleApiClient.isConnected()){
                    mGoogleApiClient.disconnect();
                }
                e.onComplete();
            }
        })).take(1);
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
