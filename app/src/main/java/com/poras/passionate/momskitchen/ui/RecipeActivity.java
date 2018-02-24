package com.poras.passionate.momskitchen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.data.model.Ingredients;
import com.poras.passionate.momskitchen.data.model.Recipes;
import com.poras.passionate.momskitchen.data.model.Steps;
import com.poras.passionate.momskitchen.fragments.IngredientFragment;
import com.poras.passionate.momskitchen.fragments.IngredientsDetailFragment;
import com.poras.passionate.momskitchen.fragments.StepsDetailFragment;
import com.poras.passionate.momskitchen.fragments.StepsListFragment;
import com.poras.passionate.momskitchen.utils.KitchenUtils;
import com.poras.passionate.momskitchen.widgets.KitchenWidgetService;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements
        StepsListFragment.OnRecipeStepListener, IngredientFragment.OnRecipeIngredientClick {
    public static String recipeName = "Recipe";
    public static ArrayList<Ingredients> ingredients = new ArrayList<>();
    private Recipes mRecipes;
    private ArrayList<Steps> mSteps;
    private FragmentManager fragmentManager;
    private int mCurrentStepPosition;
    private boolean mDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        mRecipes = getIntent().getParcelableExtra(KitchenUtils.EXTRA_RECIPE);
        mSteps = mRecipes.getSteps();
        fragmentManager = getSupportFragmentManager();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mRecipes.getName());
        }
        mDualPane = getResources().getBoolean(R.bool.isTablet);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(KitchenUtils.EXTRA_STEPS, mSteps);
            IngredientFragment ingredientFragment = new IngredientFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipeIngredientContainer, ingredientFragment).commit();
            StepsListFragment stepsListFragment = new StepsListFragment();
            stepsListFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.recipeStepsContainer, stepsListFragment).commit();
            if (mDualPane) {
                showIngredients();
            }
        }

    }

    private void showIngredients() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KitchenUtils.EXTRA_INGREDIENTS, mRecipes.getIngredients());
        IngredientsDetailFragment fragment = new IngredientsDetailFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.dual_pane_container, fragment)
                .commit();
    }

    private void showStepDetails(int stepPosition) {
        Bundle stepBundle = new Bundle();
        stepBundle.putParcelable(KitchenUtils.EXTRA_STEP, mSteps.get(stepPosition));
        stepBundle.putBoolean(KitchenUtils.EXTRA_IN_DUAL_PANE, true);
        StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
        stepsDetailFragment.setArguments(stepBundle);
        fragmentManager.beginTransaction()
                .replace(R.id.dual_pane_container, stepsDetailFragment)
                .commit();
    }

    @Override
    public void onIngredientListClicked() {
        if (mDualPane) {
            showIngredients();
        } else {
            Intent ingredientIntent = new Intent(this, IngredientsActivity.class);
            ingredientIntent.putParcelableArrayListExtra(KitchenUtils.EXTRA_INGREDIENTS,
                    mRecipes.getIngredients());
            startActivity(ingredientIntent);
        }
    }

    @Override
    public void onRecipeStepSelected(int position) {
        if (mDualPane) {
            mCurrentStepPosition = position;
            showStepDetails(position);
        } else {
            Intent stepDescIntent = new Intent(this, StepsActivity.class);
            stepDescIntent.putParcelableArrayListExtra(KitchenUtils.EXTRA_STEPS, mSteps);
            stepDescIntent.putExtra(KitchenUtils.EXTRA_POSITION, position);
            stepDescIntent.putExtra("title", mRecipes.getName());
            startActivity(stepDescIntent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KitchenUtils.EXTRA_CURRENT_POSITION, mCurrentStepPosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_widget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_widget) {
            recipeName = mRecipes.getName();
            ingredients = mRecipes.getIngredients();
            KitchenWidgetService.startActionChangeRecipeIngredients(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
