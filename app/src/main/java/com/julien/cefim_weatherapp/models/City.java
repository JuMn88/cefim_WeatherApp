package com.julien.cefim_weatherapp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class City {

    public int mIdCity;
    public String mName;
    public double mLatitude;
    public double mLongitude;

    public String mDescription;
    public String mTemperature;
    public int mWeatherIcon;
    public int mWeatherResIconWhite;

    public City(String mName, String mDescription, String mTemperature, int mWeatherIcon) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mTemperature = mTemperature;
        this.mWeatherIcon = mWeatherIcon;
    }

    public City(String stringJson) throws JSONException {
        JSONObject json = new JSONObject(stringJson);
        mName = json.getString("name");
    }
}
