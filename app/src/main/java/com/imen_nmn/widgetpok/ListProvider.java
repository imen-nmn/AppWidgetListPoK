package com.imen_nmn.widgetpok;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.bumptech.glide.Glide;
import com.imen_nmn.widgetpok.greenDaoUtils.DaoManager;
import com.imen_nmn.widgetpok.greenDaoUtils.ResultLocation;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 * 
 */
public class ListProvider implements RemoteViewsFactory {
	private List<ResultLocation> listItemList = new ArrayList<ResultLocation>();
	private Context context = null;
	private int appWidgetId;
	private final String TAG ="WListrTag-FC" ;

	public ListProvider(Context context, Intent intent) {
		Log.i(TAG, "ListProvider constructor ");

		this.context = context;
		appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

		populateListItem();
	}

	private void populateListItem() {
//		for (int i = 0; i < 10; i++) {
//			ListItem listItem = new ListItem();
//			listItem.heading = "Heading" + i;
//			listItem.content = i
//					+ " This is the content of the app widget listview.Nice content though";
//			listItemList.add(listItem);
//		}

	}

	@Override
	public int getCount() {
		Log.i(TAG, "ListProvider getCount ");

		return listItemList.size();
	}

	@Override
	public long getItemId(int position) {
		Log.e(TAG, "ListProvider getItemId "+position);

		return position;
	}



	/*
	 *Similar to getView of Adapter where instead of View
	 *we return RemoteViews 
	 * 
	 */
	@Override
	public RemoteViews getViewAt(int position) {
		Log.i(TAG, "ListProvider getViewAt "+position);

		final RemoteViews remoteView = new RemoteViews(
				context.getPackageName(), R.layout.list_row);
		final ResultLocation listItem = listItemList.get(position);
		remoteView.setTextViewText(R.id.heading, listItem.getRegion());

		remoteView.setTextViewText(R.id.content, listItem.toString());

		long randomValue = Utils.getRandomLong(listItem.getResultCondition().getId()) ;

		if(randomValue%(position+1)==0){
			remoteView.setInt(R.id.late_error, "setVisibility", View.VISIBLE);
		} else {
			remoteView.setInt(R.id.late_error, "setVisibility", View.GONE);
		}

		// Next, set a fill-intent, which will be used to fill in the pending intent template
		// that is set on the collection view in StackWidgetProvider.
		Bundle extras = new Bundle();
		extras.putInt("position", position);
		extras.putString("msg", "=> "+position+" object = "+listItem.getResultCondition()+" "+randomValue);

		Intent fillInIntent = new Intent();

		fillInIntent.putExtras(extras);
		// Make it possible to distinguish the individual on-click
		// action of a given item
		remoteView.setOnClickFillInIntent(R.id.holder_item, fillInIntent);



		new AsyncTask<Void, Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground(Void... params) {
				Looper.prepare();
				Bitmap theBitmap =null ;
				try {
					theBitmap = Glide.
							with(context).
							load(listItem.getResultCondition().getWeatherIconUrl()).
							asBitmap().
							into(-1,-1).
							get();
				} catch (final ExecutionException e) {
					Log.e(TAG, e.getMessage());
				} catch (final InterruptedException e) {
					Log.e(TAG, e.getMessage());
				}
				return theBitmap;
			}
			@Override
			protected void onPostExecute(Bitmap resBitmap) {
				if (null != resBitmap) {
					// The full bitmap should be available here

//                    views.setInt(R.id.imageViewWidget,"setBackgroundResource", R.drawable.ic_launcher);

					remoteView.setImageViewBitmap(R.id.imageView, resBitmap);
					Log.e(TAG, "Image loaded  \n  "+resBitmap.getWidth()+" x "+resBitmap.getHeight());

				};
			}
		}.execute();

		return remoteView;
	}
	

	@Override
	public RemoteViews getLoadingView() {
		Log.i(TAG, "ListProvider getLoadingView ");
		return  new RemoteViews(context.getPackageName(), R.layout.loading);
	}

	@Override
	public int getViewTypeCount() {
		Log.i(TAG, "ListProvider getViewTypeCount ");

		return 1;
	}

	@Override
	public boolean hasStableIds() {
		Log.i(TAG, "ListProvider hasStableIds ");

		return true;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "ListProvider onCreate ");

	}

	@Override
	public void onDataSetChanged() {
		Log.i(TAG, "ListProvider onDataSetChanged ");
		listItemList = DaoManager.getInstance().getResultLocationList(context);

		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		// Build an update that holds the updated widget contents
		views.setInt(R.id.loading_layout, "setVisibility", View.GONE);
		// Push update for this widget to the home screen
		ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(thisWidget, views);

	}

	@Override
	public void onDestroy() {
		Log.e(TAG, "ListProvider onDestroy ");

	}



	/***********************************/
}
