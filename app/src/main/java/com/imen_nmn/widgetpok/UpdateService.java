package com.imen_nmn.widgetpok;

import android.app.Service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.imen_nmn.widgetpok.greenDaoUtils.DaoManager;
import com.imen_nmn.widgetpok.greenDaoUtils.ResultCondition;
import com.imen_nmn.widgetpok.greenDaoUtils.ResultLocation;
import com.imen_nmn.widgetpok.rest.RetrofitAPIManager;
import com.imen_nmn.widgetpok.rest.xmlResponse.SearchLocationContent;
import com.imen_nmn.widgetpok.rest.xmlResponse.SearchWeatherContent;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by imen_nmn on 23/03/17.
 */

public class UpdateService extends Service implements Callback<SearchLocationContent> {
    private final String TAG = "WListTag-SRU";
    private Intent intentBroadCasted ;

    private ResultCondition resultCondition ;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart "+startId);

        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Build the widget update for today
        Log.d(TAG, "onStartCommand "+startId+" "+flags);

        RetrofitAPIManager.consumeWeatherApi(new Callback<SearchWeatherContent>() {
            @Override
            public void success(SearchWeatherContent searchWeatherContent, Response response) {
                resultCondition = searchWeatherContent.getResultCondition() ;
                Log.d(TAG, "success SearchWeatherContent  "+searchWeatherContent.getResultCondition().toString()+" \n "+response.toString());
                invokeApi();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure SearchWeatherContent  "+error.toString());
                List<ResultLocation> resultLocations = DaoManager.getInstance().getResultLocationList(UpdateService.this) ;
                intentBroadCasted = new Intent();
                if(resultLocations == null || resultLocations.size() == 0) {
                    intentBroadCasted.setAction(WidgetProvider.DISPLAY_ERROR_MSG) ;
                    intentBroadCasted.putExtra(WidgetProvider.ERROR_MSG, "An error has occurred");
                }
                sendBroadcast(intentBroadCasted);
                stopSelf();
            }
        }) ;

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy ");
        intentBroadCasted = new Intent() ;
        intentBroadCasted.setAction(WidgetProvider.SERVICE_STOPPED) ;
        sendBroadcast(intentBroadCasted);
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind ");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind ");
        super.onRebind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void invokeApi() {
        Log.d(TAG, "invokeApi ");
       // updateWidgetLoadingState(this);
        intentBroadCasted = new Intent(WidgetProvider.LOADING_STATE) ;
        sendBroadcast(intentBroadCasted);
        RetrofitAPIManager.consumeLocationApi(UpdateService.this);
    }

    @Override
    public void success(SearchLocationContent searchLocationContent, Response response) {
        Log.i(TAG, "success "+resultCondition);

        for(int i=0 ; i < searchLocationContent.getResults().size() ; i++) {
            Log.e(TAG, i+") "+resultCondition.toString());
            Long id = new Long(i*100) ;
            resultCondition.setId(id);
            DaoManager.getInstance().saveResultCondition(resultCondition, this);
            searchLocationContent.getResults().get(i).setResultCondition(resultCondition);

        }
        DaoManager.getInstance().saveResultLocationList(searchLocationContent.getResults(), getApplicationContext());

        intentBroadCasted = new Intent() ;
        if(searchLocationContent.getResults().size() == 0){
            intentBroadCasted.setAction(WidgetProvider.DISPLAY_ERROR_MSG) ;
            intentBroadCasted.putExtra(WidgetProvider.ERROR_MSG, getString(R.string.empty_string) );
        } else {
            intentBroadCasted.setAction(WidgetProvider.REFRESH_LOCATION_LIST) ;
        }
        sendBroadcast(intentBroadCasted);
        stopSelf();
    }

    @Override
    public void failure(RetrofitError error) {
        List<ResultLocation> resultLocations = DaoManager.getInstance().getResultLocationList(this) ;

        intentBroadCasted = new Intent();

        if(resultLocations == null || resultLocations.size() == 0) {
            intentBroadCasted.setAction(WidgetProvider.DISPLAY_ERROR_MSG) ;
            intentBroadCasted.putExtra(WidgetProvider.ERROR_MSG, "An error has occurred");
        }
        sendBroadcast(intentBroadCasted);
        stopSelf();
    }

    /******************************************/
}
