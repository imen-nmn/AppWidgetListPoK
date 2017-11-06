package com.imen_nmn.widgetpok.rest;

import android.util.Log;

import com.imen_nmn.widgetpok.Utils;
import com.imen_nmn.widgetpok.rest.jsonResponse.SearchLocationContentJson;
import com.imen_nmn.widgetpok.rest.xmlResponse.SearchLocationContent;
import com.imen_nmn.widgetpok.rest.xmlResponse.SearchWeatherContent;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.converter.SimpleXMLConverter;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by mobilepowered on 21/03/17.
 */

public class RetrofitAPIManager {

    static final String BASE_LOCATION_URL="http://api.worldweatheronline.com/premium/v1" ;
    static final String KEY_LOCATION_API="b8fbf26249a74bb0bf5150645171703" ;

    static final String query ="36.8950571,10.185602";

    private static final String TAG = "WListTag-RET";

    static public void consumeLocationApi(Callback<SearchLocationContent> callback) {
        Log.i(TAG, "consumeLocationApi " + BASE_LOCATION_URL);

        // this will build full path of API url where we want to send data.
        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(BASE_LOCATION_URL)
                .setConverter(new SimpleXMLConverter())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        // SubmitAPI is name of our interface which will send data to server.
        SearchLocationInterface api = restAdapter.
                create(SearchLocationInterface.class);


        api.sendLocationApi(KEY_LOCATION_API, Utils.getRandomInteger(23)+"", query, "xml" ,callback);
    }

    static public void consumeLocationJSONApi(Callback<SearchLocationContentJson> callback) {
        Log.i(TAG, "consumeLocationApi " + BASE_LOCATION_URL);

        // this will build full path of API url where we want to send data.
        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(BASE_LOCATION_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        // SubmitAPI is name of our interface which will send data to server.
        SearchLocationInterface api = restAdapter.
                create(SearchLocationInterface.class);


        api.sendLocationJsonApi(KEY_LOCATION_API, Utils.getRandomInteger(23)+"", query, "json" ,callback);
    }


    static public void consumeWeatherApi( Callback<SearchWeatherContent> response) {
        Log.i(TAG, "consumeWeatherApi " + BASE_LOCATION_URL);

        // this will build full path of API url where we want to send data.
        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setEndpoint(BASE_LOCATION_URL)
                .setConverter(new SimpleXMLConverter())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        // SubmitAPI is name of our interface which will send data to server.
        SearchWeatherConditionInterface api = restAdapter.
                create(SearchWeatherConditionInterface.class);

        api.sendWwoApi("no",KEY_LOCATION_API, Utils.getRandomInteger(23)+"", query,"no", "xml", response);
    }



    public interface SearchLocationInterface {

        @POST("/search.ashx")
        void sendLocationApi(@Query("key") String key,
                             @Query("num_of_results") String num_of_results,
                             @Query("query") String query,
                             @Query("format") String format,
                             Callback<SearchLocationContent> response);


        @POST("/search.ashx")
        void sendLocationJsonApi(@Query("key") String key,
                             @Query("num_of_results") String num_of_results,
                             @Query("query") String query,
                             @Query("format") String format,
                             Callback<SearchLocationContentJson> response);
    }

    public interface SearchWeatherConditionInterface {

        @POST("/weather.ashx")
        void sendWwoApi(@Query("fx") String fx,
                        @Query("key") String key,
                        @Query("num_of_days") String num_of_days,
                        @Query("q") String query,
                        @Query("show_comments") String show_comments,
                        @Query("format") String format,
                        Callback<SearchWeatherContent> response);
    }

    /**
     *
     * new Callback<SearchLocationContent>() {
    @Override
    public void success(SearchLocationContent searchLocationContent, Response response) {
    Log.i(TAG, "searchLocationContent success " + searchLocationContent.getResults().toString());
    }

    @Override
    public void failure(RetrofitError error) {
    Log.i(TAG, "searchLocationContent failure " + error.toString());
    }
    }3*/
}
