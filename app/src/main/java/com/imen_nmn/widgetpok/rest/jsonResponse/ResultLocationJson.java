package com.imen_nmn.widgetpok.rest.jsonResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by imen_nmn on 21/03/17.
 */


public class ResultLocationJson implements Serializable {
    @SerializedName("areaName")
    @Expose
    public ArrayList<ValueJson> areaName;
    @SerializedName("country")
    @Expose
    public ArrayList<ValueJson>  country;
    @SerializedName("region")
    @Expose
    public ArrayList<ValueJson>  region;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("population")
    @Expose
    public String population;
    @SerializedName("weatherUrl")
    @Expose
    public ArrayList<ValueJson>  weatherUrl;

    public ArrayList<ValueJson> getAreaName() {
        return areaName;
    }

    public void setAreaName(ArrayList<ValueJson> areaName) {
        this.areaName = areaName;
    }

    public ArrayList<ValueJson> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<ValueJson> country) {
        this.country = country;
    }


    public ArrayList<ValueJson> getRegion() {
        return region;
    }

    public void setRegion(ArrayList<ValueJson> region) {
        this.region = region;
    }

    public void setWeatherUrl(ArrayList<ValueJson> weatherUrl) {
        this.weatherUrl = weatherUrl;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }


    public ArrayList<ValueJson> getWeatherUrl() {
        return weatherUrl;
    }

    private class ValueJson implements Serializable {
        @SerializedName("value")
        @Expose

        public String value ;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
