package com.poras.passionate.momskitchen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.data.model.Recipes;
import com.poras.passionate.momskitchen.utils.KitchenUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipeViewHolder> {
    private ArrayList<Recipes> mRecipes;
    private final Context mContext;
    private final OnClickRecipe mHandler;

    public interface OnClickRecipe {
        void onRecipeClicked(Recipes recipe);
    }

    public RecipesListAdapter(Context context, OnClickRecipe handler) {
        this.mContext = context;
        this.mHandler = handler;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recipeView = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipes recipe = mRecipes.get(position);
        String name = recipe.getName();
        holder.recipeName.setText(name);
        String recipeImage = recipe.getImage();
        int recipeImageId = KitchenUtils.getImageIdWithName(name);
        if (TextUtils.isEmpty(recipeImage)) {
            Picasso.with(mContext).load(recipeImageId).
                    placeholder(R.drawable.default_image).into(holder.recipeImage);
        } else {
            Picasso.with(mContext).load(recipeImage).
                    placeholder(R.drawable.default_image).into(holder.recipeImage);
        }

    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_recipe)
        ImageView recipeImage;
        @BindView(R.id.tv_recipe_name)
        TextView recipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mHandler.onRecipeClicked(mRecipes.get(getAdapterPosition()));
        }
    }

    public void setRecipeList(ArrayList<Recipes> recipes) {
        if (recipes != null && recipes.size() > 0) {
            this.mRecipes = recipes;
            notifyDataSetChanged();
        }
    }

    private int getCount() {
        if (mRecipes != null)
            return mRecipes.size();
        else
            return 0;
    }

}
