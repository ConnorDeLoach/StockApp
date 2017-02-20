package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Allows remote adapter to request views
 */

public class MyRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("test", "remoteviews service running?");
        return (new MyRemoteViewsFactory(this.getApplicationContext(), intent));
    }
}
