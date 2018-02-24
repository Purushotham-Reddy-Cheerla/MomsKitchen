package com.poras.passionate.momskitchen.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.poras.passionate.momskitchen.R;


public class RecipeWidgetProvider extends AppWidgetProvider {
    private static void updateWidget(Context context, AppWidgetManager widgetManager, String recipeName, int widgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients);
        views.setTextViewText(R.id.recipeName, recipeName);
        Intent intent = new Intent(context, IngredientsService.class);
        views.setRemoteAdapter(R.id.widget_ingredients, intent);
        widgetManager.updateAppWidget(widgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        KitchenWidgetService.startActionChangeRecipeIngredients(context);
    }

    public static void updateRecipeIngredients(Context context, AppWidgetManager widgetManager, String recipeName, int[] widgetIds) {
        for (int widgetId : widgetIds) {
            updateWidget(context, widgetManager, recipeName, widgetId);
        }
    }

}
