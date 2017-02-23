package com.udacity.stockhawk.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.PrefUtils;
import com.udacity.stockhawk.data.Utils;

/**
 * Adapter for remote views to attack to widget listview
 */

public class MyRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private int mAppId;
    private Cursor cursor;

    public MyRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long serviceUID = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(
                Contract.Quote.URI,
                Contract.Quote.QUOTE_COLUMNS.toArray(new String[]{}),
                null,
                null,
                Contract.Quote.COLUMN_SYMBOL);
        Binder.restoreCallingIdentity(serviceUID);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);

        // Get data from cursor
        cursor.moveToPosition(position);
        String sym = cursor.getString(Contract.Quote.POSITION_SYMBOL);
        float price = cursor.getFloat(Contract.Quote.POSITION_PRICE);

        // Wire data to views
        view.setTextViewText(R.id.symbol, sym);
        view.setTextViewText(R.id.price, Utils.getDollarFormat().format(price));

        float rawAbsoluteChange = cursor.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE);
        float percentageChange = cursor.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE);

        if (rawAbsoluteChange > 0) {
            view.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_green);
        } else {
            view.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
        }

        String change = Utils.getDollarFormatWithPlus().format(rawAbsoluteChange);
        String percentage = Utils.getPercentageFormat().format(percentageChange / 100);

        if (PrefUtils.getDisplayMode(mContext).equals(mContext.getString(R.string.pref_display_mode_absolute_key))) {
            view.setTextViewText(R.id.change, change);
        } else {
            view.setTextViewText(R.id.change, percentage);
        }

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
