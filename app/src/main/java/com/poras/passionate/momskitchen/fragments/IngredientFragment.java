package com.poras.passionate.momskitchen.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poras.passionate.momskitchen.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientFragment extends Fragment {

    private OnRecipeIngredientClick mClickHandler;

    @BindView(R.id.tv_recipe_ingredient)
    TextView recipeClickable;

    public IngredientFragment() {
    }

    public interface OnRecipeIngredientClick {
        void onIngredientListClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeIngredientClick) {
            mClickHandler = (OnRecipeIngredientClick) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ingredientView = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, ingredientView);
        return ingredientView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickHandler.onIngredientListClicked();
            }
        });
    }

}
