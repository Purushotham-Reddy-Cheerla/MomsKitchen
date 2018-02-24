package com.poras.passionate.momskitchen.widgets;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.data.model.Ingredients;
import com.poras.passionate.momskitchen.ui.RecipeActivity;

import java.util.ArrayList;


class IngredientsServiceViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();

    IngredientsServiceViewFactory(Context context) {
        this.mContext = context;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        this.ingredients = RecipeActivity.ingredients;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null) return 0;
        else
            return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteItemView = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_item);
        remoteItemView.setTextViewText(R.id.ingredient, ingredients.get(position).getIngredient());
        remoteItemView.setTextViewText(R.id.quantity_val, mContext.getString(R.string.measure_units,
                ingredients.get(position).getQuantity(), ingredients.get(position).getMeasure()));
        return remoteItemView;
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
