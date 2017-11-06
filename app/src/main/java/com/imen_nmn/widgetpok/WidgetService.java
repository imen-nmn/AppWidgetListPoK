package com.imen_nmn.widgetpok;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.imen_nmn.widgetpok.greenDaoUtils.DaoManager;

public class WidgetService extends RemoteViewsService  {
	/*
	 * So pretty simple just defining the Adapter of the listview
	 * here Adapter is ListProvider
	 * */
	private final String TAG ="WListTag-SR" ;


	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		Log.i(TAG, "ListProvider onGetViewFactory ");
		return new ListProvider(this.getApplicationContext(), intent) ;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate ");

	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.d(TAG, "onStart "+startId);

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStart "+startId);

		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		boolean isDbClosed = DaoManager.getInstance().closeGreenDaoDB() ;
		Log.d(TAG, "onDestroy isDbClosed "+isDbClosed);

	}
}
