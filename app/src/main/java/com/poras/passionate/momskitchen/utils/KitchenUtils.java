package com.poras.passionate.momskitchen.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.poras.passionate.momskitchen.R;

public class KitchenUtils {

    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_STEPS = "steps";
    public static final String EXTRA_STEP_COUNT = "stepsCount";
    public static final String EXTRA_STEP = "step";
    public static final String EXTRA_IS_INGREDIENT = "isIngredient";
    public static final String EXTRA_CURRENT_POSITION = "currentPosition";
    public static final String EXTRA_INGREDIENTS = "ingredients";
    public static final String EXTRA_RECIPE = "recipe";
    public static final String EXTRA_RECIPE_LIST = "recipeList";
    public static final String EXTRA_IN_DUAL_PANE = "inDualPane";

    private static final String NUTELLA = "Nutella Pie";
    private static final String BROWNIES = "Brownies";
    private static final String YELLOW_CAKE = "Yellow Cake";
    private static final String CHEESE_CAKE = "Cheesecake";

    public static int getImageIdWithName(String name) {
        int imageId;
        switch (name) {
            case NUTELLA:
                imageId = R.drawable.nutella_pie;
                break;
            case BROWNIES:
                imageId = R.drawable.brownie;
                break;
            case YELLOW_CAKE:
                imageId = R.drawable.yellow_cake;
                break;
            case CHEESE_CAKE:
                imageId = R.drawable.cheese_cake;
                break;
            default:
                imageId = R.drawable.default_image;
        }

        return imageId;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return !((info == null) || (info.getType() != ConnectivityManager.TYPE_MOBILE && info.getType() != ConnectivityManager.TYPE_WIFI)
                    || !(info.isConnected()));
        } else {
            return false;
        }

    }

    public static void showAlertDialog(Context context, String message) {
        (new AlertDialog.Builder(context).setTitle(context.getString(R.string.alert_title)).setMessage(message)
                .setNegativeButton(context.getString(R.string.ok_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create()).show();
    }
}
