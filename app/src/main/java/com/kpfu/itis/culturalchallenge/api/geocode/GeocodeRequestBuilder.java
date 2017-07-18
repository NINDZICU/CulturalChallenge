package com.kpfu.itis.culturalchallenge.api.geocode;

/**
 * Created by Rage on 18.07.2017.
 */

public class GeocodeRequestBuilder {

    private String mStreet;
    private String mCity;
    private String mCountry;
    private String mHouse;

    public GeocodeRequestBuilder setStreet(String street) {
        mStreet = street.trim().replaceAll(" ","+");
        return this;
    }

    public GeocodeRequestBuilder setCity(String city) {
        mCity = city.trim().replaceAll(" ","+");
        return this;
    }

    public GeocodeRequestBuilder setCountry(String country) {
        mCountry = country.trim().replaceAll(" ","+");
        return this;
    }

    public GeocodeRequestBuilder setHouse(String house) {
        mHouse = house.trim().replaceAll(" ","+");
        return this;
    }

    String build(){
        StringBuilder request=new StringBuilder();
        request.append(mStreet);
        request.append("+");
        request.append(mHouse);
        request.append("+");
        request.append(mCity);
        request.append("+");
        request.append(mCountry);
        return request.toString();
    }
}
