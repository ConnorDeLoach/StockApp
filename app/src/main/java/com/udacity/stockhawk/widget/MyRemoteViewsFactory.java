package com.udacity.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

/**
 * Adapter for remote views to attack to widget listview
 */

public class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mAppId;
    private Cursor mCursor;

    public MyRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = mContext.getContentResolver().query(
                Contract.Quote.URI,
                Contract.Quote.QUOTE_COLUMNS.toArray(new String[]{}),
                null,
                null,
                Contract.Quote.COLUMN_SYMBOL);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        // Get data from cursor
        mCursor.moveToPosition(position);
        String sym = mCursor.getString(Contract.Quote.POSITION_SYMBOL);

        // Wire data to views
        RemoteViews list = new RemoteViews(mContext.getPackageName(), R.layout.widget_layout);
        list.setTextViewText(R.id.widget_textView, sym);
        return list;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
