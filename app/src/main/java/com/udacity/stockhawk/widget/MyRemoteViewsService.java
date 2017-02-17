package com.udacity.stockhawk.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Allows remote adapter to request views
 */

public class MyRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new MyRemoteViewsFactory(this.getApplicationContext(), intent));
    }
}
