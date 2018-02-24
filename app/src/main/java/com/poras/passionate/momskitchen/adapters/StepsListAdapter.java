package com.poras.passionate.momskitchen.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.data.model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsViewHolder> {
    private ArrayList<Steps> mSteps;
    private final OnRecipeStepClickHandler mHandler;
    private final Context mContext;
    private int mSelectedPosition = -1;

    public interface OnRecipeStepClickHandler {
        void onRecipeStepClicked(int position);
    }

    public StepsListAdapter(Context context, OnRecipeStepClickHandler handler) {
        this.mHandler = handler;
        this.mContext = context;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View stepListView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_item, parent, false);
        return new StepsViewHolder(stepListView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        String shortDesc = mContext.getString(R.string.step_num, position, mSteps.get(position).getShortDescription());
        holder.stepShortDesc.setText(shortDesc);

        Resources resources = mContext.getResources();
        if (mSelectedPosition == position) {
            holder.itemView.setBackground(resources.getDrawable(R.drawable.list_item_selected_background));
        } else {
            holder.itemView.setBackground(resources.getDrawable(R.drawable.list_item_background));
        }

    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_short_dsc)
        TextView stepShortDesc;

        StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
            mHandler.onRecipeStepClicked(getAdapterPosition());
            notifyItemChanged(mSelectedPosition);
            mSelectedPosition = getAdapterPosition();
            notifyItemChanged(mSelectedPosition);
        }
    }

    public void setRecipeSteps(ArrayList<Steps> steps) {
        if (steps != null && steps.size() > 0) {
            this.mSteps = steps;
            notifyDataSetChanged();
        }
    }

    private int getCount() {
        if (mSteps != null && mSteps.size() > 0)
            return mSteps.size();
        else
            return 0;
    }


}
