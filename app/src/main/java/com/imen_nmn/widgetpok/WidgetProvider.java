package com.imen_nmn.widgetpok;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

    /*
     * If we want to change an id of the api invoked , you should stop the current service and cancel the alarm manager ,
     * then restart the service with new intent that include the new params and data
	 */

public class WidgetProvider extends AppWidgetProvider {

    public static final String REFRESH_LOCATION_LIST = "com.imen.nmn.REFRESH_LOCATION_LIST";
    public static final String LOADING_STATE = "com.imen.nmn.LOADING_STATE";
    public static final String DISPLAY_ERROR_MSG = "com.imen.nmn.DISPLAY_ERROR_MSG";
    public static final String SERVICE_STOPPED = "com.imen.nmn.SERVICE_STOPPED";

    public static final String TOAST_ACTION = "TOAST_ACTION";
    public static final String ERROR_MSG = "error_msg";
    public static final String REFRESH_MANUAL = "REFRESH_BTN";

    private final String TAG = "WListTag-PR";
    private PendingIntent pendingIntent = null;

    /*
     * this method is called every 30 mins as specified on widgetinfo.xml
	 * this method is also called on every phone reboot
	 */


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Log.e(TAG, "onUpdate");
        updateWidgetLoadingState(context, View.VISIBLE);
        triggerAlarm(context);
        initAllWidgets(context, appWidgetManager, appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews initWidgetListView(Context context, int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        remoteViews.setInt(R.id.loading_layout, "setVisibility", View.VISIBLE);
        remoteViews.setInt(R.id.error_view, "setVisibility", View.GONE);
        remoteViews.setInt(R.id.listViewWidget, "setVisibility", View.VISIBLE);

        final Intent onClickIntent = new Intent(context, WidgetProvider.class);
        onClickIntent.setAction(TOAST_ACTION);
        onClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        onClickIntent.setData(Uri.parse(onClickIntent.toUri(Intent.URI_INTENT_SCHEME)));


        final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, 0,
                onClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.listViewWidget, onClickPendingIntent);

        // Bind the click intent for the refresh button on the widget
        final Intent refreshIntent = new Intent(context, WidgetProvider.class);
        refreshIntent.setAction(WidgetProvider.REFRESH_MANUAL);
        final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
                refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.refresh_btn, refreshPendingIntent);


        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget,
                svcIntent);

        context.sendBroadcast(svcIntent);
        //setting an empty view in case of no data
//		remoteViews.setEmptyView(R.id.listViewWidget, R.id.loading_layout);
        return remoteViews;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.e(TAG, "ListProvider onReceive  " + intent.getAction() + "  \n extra = " + intent.getExtras());

        if (intent.getAction().equals(TOAST_ACTION)) {
            String msg = intent.getStringExtra("msg");
            Toast.makeText(context,  msg, Toast.LENGTH_SHORT).show();
        }

        if (intent.getAction().equals(LOADING_STATE)) {
            updateWidgetLoadingState(context, View.VISIBLE);
        }

        if(intent.getAction().equals(SERVICE_STOPPED)){
           updateWidgetLoadingState(context, View.GONE) ;
        }

        if(intent.getAction().equals(REFRESH_MANUAL)){
            updateWidgetLoadingState(context, View.VISIBLE);
            stopAlarm(context);
            triggerAlarm(context);
        }

        if (intent.getAction().equals(REFRESH_LOCATION_LIST)) {
            updateWidgetList(context);
        }

        if (intent.getAction().equals(DISPLAY_ERROR_MSG)) {
            String errorMsg = intent.getStringExtra(ERROR_MSG);
            updateWidgetError(errorMsg, context);
        }

        if ((intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                || (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)))) {
            boolean isConnected = NetworkUtil.isOnline(context);
            String msg = "Not Connected ";

            if (isConnected) {
                msg = " You are online ";
                Log.e(TAG, "setRepeating alarm ");
                triggerAlarm(context);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listViewWidget);
            } else {
                stopAlarm(context);
                Log.e(TAG, "cancel alarm ");
            }

            Toast.makeText(context, "Network change " + msg, Toast.LENGTH_SHORT).show();

        }
    }


    private void initAllWidgets(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds) {
        Log.i(TAG, "updateAllWidgets");
		/*int[] appWidgetIds holds ids of multiple instance of your widget
		 * meaning you are placing more than one widgets on your homescreen*/
        for (int i = 0; i < appWidgetIds.length; ++i) {
            RemoteViews remoteViews = initWidgetListView(context,
                    appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i(TAG, "onDeleted " + appWidgetIds.length);
        if (appWidgetIds.length == 0) {
            stopAlarm(context);
        }
    }

    @Override
    public void onEnabled(Context context) {
        Log.e(TAG, "onEnabled");
        super.onEnabled(context);

    }


    private void triggerAlarm(Context context) {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (pendingIntent == null) {
            final Intent intent = new Intent(context, UpdateService.class);
            pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 30*60000, pendingIntent);
    }

    private void stopAlarm(Context context) {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(context, UpdateService.class);
        pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        context.stopService(intent) ;
    }

    /*******************************************************/

    void updateWidgetError(String errorMsg, Context context) {
        RemoteViews views = null;
        Log.d(TAG, "updateWidgetError ");
        views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Build an update that holds the updated widget contents
        views.setInt(R.id.loading_layout, "setVisibility", View.GONE);
        views.setInt(R.id.error_view, "setVisibility", View.VISIBLE);
        views.setInt(R.id.listViewWidget, "setVisibility", View.GONE);

        views.setTextViewText(R.id.error_view, errorMsg);
        // Push update for this widget to the home screen
        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thisWidget, views);
    }

    void updateWidgetList(Context context) {
        // The data has changed, so notify the widget that the collection view needs to be updated.
        // In response, the factory's onDataSetChanged() will be called which will requery the
        // cursor for the new data.
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setInt(R.id.loading_layout, "setVisibility", View.VISIBLE);
        views.setInt(R.id.error_view, "setVisibility", View.GONE);
        views.setInt(R.id.listViewWidget, "setVisibility", View.VISIBLE);

        ComponentName theWidget = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(context);

        mAppWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetManager.getAppWidgetIds(theWidget), R.id.listViewWidget);
        mAppWidgetManager.updateAppWidget(theWidget, views);
    }

    void updateWidgetLoadingState(Context context, int visible) {
        Log.d(TAG, "updateWidgetLoadingState ");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        // Build an update that holds the updated widget contents
        views.setInt(R.id.loading_layout, "setVisibility", visible);
        // Push update for this widget to the home screen
        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(thisWidget, views);
    }


    /**********************************************************/

}
