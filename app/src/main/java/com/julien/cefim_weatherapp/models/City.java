package com.julien.cefim_weatherapp.models;

import com.julien.cefim_weatherapp.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class City {

    public int mIdCity;
    public String mName;
    public double mLatitude;
    public double mLongitude;

    public String mDescription;
    public String mTemperature;
    public int mWeatherIconGrey;
    public int mWeatherResIconWhite;
    public String mStringJson;

    public City(String mName, String mDescription, String mTemperature, int mWeatherIconGrey) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mTemperature = mTemperature;
        this.mWeatherIconGrey = mWeatherIconGrey;
    }

    public City(String stringJson) throws JSONException {
        mStringJson = stringJson;
        JSONObject json = new JSONObject(stringJson);

        JSONObject details = json.getJSONArray("weather").getJSONObject(0);
        JSONObject main = json.getJSONObject("main");
        JSONObject coord = json.getJSONObject("coord");

        mIdCity = json.getInt("id");
        mName = json.getString("name");
        mLatitude = coord.getDouble("lat");
        mLongitude = coord.getDouble("lon");
        mDescription = Util.capitalize(details.getString("description"));
        mTemperature = String.format("%.0f", main.getDouble("temp")) + " °C";
        mWeatherResIconWhite = Util.setWeatherIcon(
                details.getInt("id"),
                json.getJSONObject("sys").getLong("sunrise") * 1000,
                json.getJSONObject("sys").getLong("sunset") * 1000
        );
        mWeatherIconGrey = Util.setWeatherIcon(details.getInt("id"));

    }
}
