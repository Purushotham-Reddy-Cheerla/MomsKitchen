package com.poras.passionate.momskitchen;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.poras.passionate.momskitchen.ui.HomeKitchen;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeStepIngredientTest {


    @Rule
    public ActivityTestRule<HomeKitchen> homeActivityTestRule = new ActivityTestRule<>(HomeKitchen.class);

    @Test
    public void onRecipeClickInHomeActivityTest() {
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_recipe_ingredient)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_recipe_ingredient)).check(matches(withText("Ingredients")));
        onView(withId(R.id.recipe_head)).check(matches(withText("Recipe Steps")));
        onView(withId(R.id.rv_recipe_steps)).check(matches(isDisplayed()));

    }

    @Test
    public void onStepClickInRecipeActivityTest() {
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_recipe_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.btn_next)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_prev)).check(matches(not(isDisplayed())));
    }

    @Test
    public void onIngredientListClickInRecipeActivity() {
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_recipe_ingredient)).perform(click());
        onView(withId(R.id.rv_ingredients)).check(matches(isDisplayed()));
    }

    @Test
    public void onNextStepClickInStepActivity() {
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_recipe_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.btn_next)).perform(click());
        onView(withId(R.id.btn_prev)).check(matches(isDisplayed()));
    }

}
