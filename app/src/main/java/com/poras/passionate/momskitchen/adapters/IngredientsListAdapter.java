package com.poras.passionate.momskitchen.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.data.model.Ingredients;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientViewHolder> {
    private final Context mContext;
    private final ArrayList<Ingredients> mIngredients;

    public IngredientsListAdapter(Context context, ArrayList<Ingredients> ingredients) {
        this.mContext = context;
        this.mIngredients = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ingredientView = LayoutInflater.from(mContext)
                .inflate(R.layout.ingridient_list_item, parent, false);
        return new IngredientViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredients ingredient = mIngredients.get(position);
        String quantValMeasure = mContext.getString(R.string.measure_units, ingredient.getQuantity(), ingredient.getMeasure());
        holder.ingredientName.setText(ingredient.getIngredient());
        holder.quantityValMeasure.setText(quantValMeasure);
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_ingredient)
        TextView ingredientName;
        @BindView(R.id.tv_quantity_val)
        TextView quantityValMeasure;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private int getCount() {
        if (mIngredients != null && mIngredients.size() > 0)
            return mIngredients.size();
        else
            return 0;
    }
}
