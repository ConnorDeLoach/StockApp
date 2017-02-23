package com.udacity.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;

/**
 * Widget displaying stock quotes
 */

public class MyAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        // receive broadcast from QuoteSyncJob after it has updated the database
        String action = context.getString(R.string.action_data_updated);
        if (intent.getAction().equals(action)) {

            // Retrieve appIds and notifydatachange
            AppWidgetManager awm = AppWidgetManager.getInstance(context);
            ComponentName name = new ComponentName(context, MyAppWidgetProvider.class);
            int[] appWidgetIds = awm.getAppWidgetIds(name);
            awm.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview);

        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; ++i) {

            // Set up the intent that starts the RemoteViewsService, which will
            // provide the views for this collection.
            Intent intent = new Intent(context, MyRemoteViewsService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects to a RemoteViewsService  through the specified intent.
            rv.setRemoteAdapter(R.id.widget_listview, intent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
