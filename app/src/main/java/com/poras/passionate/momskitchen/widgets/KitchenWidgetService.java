package com.poras.passionate.momskitchen.widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.ui.RecipeActivity;


public class KitchenWidgetService extends IntentService {

    private static final String CLASS_NAME = KitchenWidgetService.class.getSimpleName();
    private static final String ACTION_UPDATE_RECIPE = "com.poras.passionate.momskitchen.action.RECIPE_INGREDIENTS";

    public KitchenWidgetService() {
        super(CLASS_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (ACTION_UPDATE_RECIPE.equals(intent.getAction())) {
                handleActionChangeIngredients();
            }
        }
    }

    public static void startActionChangeRecipeIngredients(Context context) {
        Intent recipeListIntent = new Intent(context, KitchenWidgetService.class);
        recipeListIntent.setAction(ACTION_UPDATE_RECIPE);
        context.startService(recipeListIntent);
    }

    private void handleActionChangeIngredients() {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = manager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients);
        RecipeWidgetProvider.updateRecipeIngredients(this, manager, RecipeActivity.recipeName, appWidgetIds);
    }
}
