package com.poras.passionate.momskitchen.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.data.model.Ingredients;
import com.poras.passionate.momskitchen.fragments.IngredientsDetailFragment;
import com.poras.passionate.momskitchen.utils.KitchenUtils;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.ingredients_title);
        }
        ArrayList<Ingredients> mIngredients = getIntent().getParcelableArrayListExtra(KitchenUtils.EXTRA_INGREDIENTS);
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(KitchenUtils.EXTRA_INGREDIENTS, mIngredients);
            IngredientsDetailFragment fragment = new IngredientsDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredientListContainer, fragment).commit();
        }
    }
}
