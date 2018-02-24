package com.poras.passionate.momskitchen.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.adapters.IngredientsListAdapter;
import com.poras.passionate.momskitchen.data.model.Ingredients;
import com.poras.passionate.momskitchen.utils.KitchenUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsDetailFragment extends Fragment {

    private ArrayList<Ingredients> mIngredients;
    @BindView(R.id.rv_ingredients)
    RecyclerView mRecyclerView;

    public IngredientsDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredients_detail, container, false);
        ButterKnife.bind(this, view);
        mIngredients = getArguments().getParcelableArrayList(KitchenUtils.EXTRA_INGREDIENTS);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IngredientsListAdapter adapter = new IngredientsListAdapter(getContext(), mIngredients);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
    }
}
