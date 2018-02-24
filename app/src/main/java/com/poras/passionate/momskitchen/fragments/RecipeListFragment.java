package com.poras.passionate.momskitchen.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.adapters.RecipesListAdapter;
import com.poras.passionate.momskitchen.data.DataFetchService;
import com.poras.passionate.momskitchen.data.model.Recipes;
import com.poras.passionate.momskitchen.utils.KitchenUtils;
import com.poras.passionate.momskitchen.utils.RetroClientJson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, RecipesListAdapter.OnClickRecipe {

    private static final int LOADER_ID = 21;
    private final Type recipesType = new TypeToken<List<Recipes>>() {
    }.getType();

    public RecipeListFragment() {
    }

    public interface RecipeClickCallBack {
        void onRecipeClick(Recipes recipe);
    }

    @BindView(R.id.rv_recipe_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.pb_list)
    ProgressBar mPb;

    private RecipesListAdapter mAdapter;
    private RecipeClickCallBack callBackHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeClickCallBack) {
            callBackHandler = (RecipeClickCallBack) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new RecipesListAdapter(getContext(), this);
        mRecyclerView.setHasFixedSize(true);
        boolean dualPane = view.findViewById(R.id.dum_view) != null;
        if (dualPane) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
        mRecyclerView.setAdapter(mAdapter);


        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        if (savedInstanceState != null) {
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            Loader<String> mLoader = loaderManager.getLoader(LOADER_ID);
            if (mLoader != null)
                loaderManager.restartLoader(LOADER_ID, null, this);
            else
                loaderManager.initLoader(LOADER_ID, null, this);

        }
        return view;
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<String>(getActivity()) {
            String mJson;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (mJson != null) {
                    deliverResult(mJson);
                } else {
                    mPb.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                try {
                    return DataFetchService.getRecipeData(DataFetchService.RECIPE_URL);
                } catch (IOException e) {
                    KitchenUtils.showAlertDialog(getContext(), getString(R.string.data_fetch_fail) + "\n" + e.toString());
                    return null;
                }
            }

            @Override
            public void deliverResult(String data) {
                mJson = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        mPb.setVisibility(View.INVISIBLE);
        if (loader.getId() == LOADER_ID && data != null) {
            List<Recipes> mRecipes = RetroClientJson.deSerializeList(data, recipesType);
            mAdapter.setRecipeList((ArrayList<Recipes>) mRecipes);
        }
    }

    @Override

    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onRecipeClicked(Recipes recipe) {
        callBackHandler.onRecipeClick(recipe);
    }
}
