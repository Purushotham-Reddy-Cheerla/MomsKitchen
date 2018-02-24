package com.poras.passionate.momskitchen.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.adapters.StepsListAdapter;
import com.poras.passionate.momskitchen.data.model.Steps;
import com.poras.passionate.momskitchen.utils.KitchenUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListFragment extends Fragment implements StepsListAdapter.OnRecipeStepClickHandler {

    @BindView(R.id.rv_recipe_steps)
    RecyclerView mRecyclerView;

    private ArrayList<Steps> mSteps;
    private OnRecipeStepListener mListener;
    private int mPosition;
    private StepsListAdapter mAdapter;

    public StepsListFragment() {
    }

    public interface OnRecipeStepListener {
        void onRecipeStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeStepListener) {
            mListener = (OnRecipeStepListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = this.getArguments();
        mSteps = args.getParcelableArrayList(KitchenUtils.EXTRA_STEPS);
        View recipeStepsView = inflater.inflate(R.layout.fragment_steps_list, container, false);
        ButterKnife.bind(this, recipeStepsView);
        setRetainInstance(true);
        return recipeStepsView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new StepsListAdapter(getContext(), this);
        mAdapter.setRecipeSteps(mSteps);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRecipeStepClicked(int position) {
        mListener.onRecipeStepSelected(position);
        mPosition = position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedPosition", mPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt("selectedPosition");
            mAdapter.notifyItemChanged(mPosition);
        }
    }
}
