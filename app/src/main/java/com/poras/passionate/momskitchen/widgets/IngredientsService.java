package com.poras.passionate.momskitchen.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;


public class IngredientsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsServiceViewFactory(this.getApplicationContext());
    }
}
