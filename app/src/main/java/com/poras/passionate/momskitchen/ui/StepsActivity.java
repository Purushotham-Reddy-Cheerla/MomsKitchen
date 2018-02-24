package com.poras.passionate.momskitchen.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.data.model.Steps;
import com.poras.passionate.momskitchen.fragments.StepsDetailFragment;
import com.poras.passionate.momskitchen.utils.KitchenUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("ConstantConditions")
public class StepsActivity extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<Steps> mSteps;
    private int mPosition;
    @BindView(R.id.btn_prev)
    Button mPrevBtn;
    @BindView(R.id.btn_next)
    Button mNextBtn;
    @BindView(R.id.nxtLayout)
    LinearLayout nextLayout;
    private int mStepsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        if (savedInstanceState == null) {
            mPosition = getIntent().getIntExtra(KitchenUtils.EXTRA_POSITION, 0);
            mSteps = getIntent().getParcelableArrayListExtra(KitchenUtils.EXTRA_STEPS);
            mStepsCount = mSteps.size();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, getFragment(mPosition))
                    .commit();
        }

        if (savedInstanceState != null)
            mStepsCount = savedInstanceState.getInt(KitchenUtils.EXTRA_STEP_COUNT);

        checkForOrientationUpdateUi(getResources().getConfiguration().orientation);
        showHidePrevButton();
        showHideNextButton();
        mNextBtn.setOnClickListener(this);
        mPrevBtn.setOnClickListener(this);
    }

    private void checkForOrientationUpdateUi(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
            nextLayout.setVisibility(View.GONE);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            nextLayout.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
        }

    }

    private void showHidePrevButton() {
        if (mPosition > 0)
            mPrevBtn.setVisibility(View.VISIBLE);
        else
            mPrevBtn.setVisibility(View.GONE);
    }

    private void showHideNextButton() {
        if (mPosition == mStepsCount - 1)
            mNextBtn.setVisibility(View.GONE);
        else
            mNextBtn.setVisibility(View.VISIBLE);
    }

    private StepsDetailFragment getFragment(int position) {
        Bundle stepBundle = new Bundle();
        stepBundle.putParcelable(KitchenUtils.EXTRA_STEP, mSteps.get(position));
        stepBundle.putBoolean("inDualPane", false);
        StepsDetailFragment newFragment = new StepsDetailFragment();
        newFragment.setArguments(stepBundle);
        return newFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_prev:
                if (mPosition > 0) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, getFragment(--mPosition))
                            .commit();
                    showHidePrevButton();
                    showHideNextButton();
                }
                break;
            case R.id.btn_next:
                if (mPosition < mStepsCount - 1) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, getFragment(++mPosition))
                            .commit();
                    showHidePrevButton();
                    showHideNextButton();
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KitchenUtils.EXTRA_STEP_COUNT, mStepsCount);
    }

}
