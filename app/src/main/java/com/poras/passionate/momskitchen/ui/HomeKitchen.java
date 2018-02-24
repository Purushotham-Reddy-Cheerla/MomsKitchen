package com.poras.passionate.momskitchen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.data.model.Recipes;
import com.poras.passionate.momskitchen.fragments.RecipeListFragment;
import com.poras.passionate.momskitchen.utils.KitchenUtils;

public class HomeKitchen extends AppCompatActivity implements RecipeListFragment.RecipeClickCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_kitchen);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_list_container, new RecipeListFragment(), KitchenUtils.EXTRA_RECIPE_LIST)
                    .commit();
        }

    }

    @Override
    public void onRecipeClick(Recipes recipe) {
        Intent recipeIntent = new Intent(this, RecipeActivity.class);
        recipeIntent.putExtra(KitchenUtils.EXTRA_RECIPE, recipe);
        startActivity(recipeIntent);
    }
}
