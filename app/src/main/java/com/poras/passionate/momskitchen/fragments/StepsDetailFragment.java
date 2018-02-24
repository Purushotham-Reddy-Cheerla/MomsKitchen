package com.poras.passionate.momskitchen.fragments;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.poras.passionate.momskitchen.R;
import com.poras.passionate.momskitchen.data.model.Steps;
import com.poras.passionate.momskitchen.utils.KitchenUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsDetailFragment extends Fragment {

    private Steps mStep;
    @BindView(R.id.exoPlayer)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.descLayout)
    LinearLayout mLayout;
    @BindView(R.id.step_desc)
    TextView mStepDesc;
    @BindView(R.id.iv_recipe_thumb)
    ImageView mThumbnail;
    private SimpleExoPlayer mPlayer;
    private MediaSource mMediaSource;
    private boolean inDualPane;
    private long playbackPosition;
    private boolean playWhenReady = true;
    private int windowIndex;

    public StepsDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View stepView = inflater.inflate(R.layout.fragment_steps_detail, container, false);
        ButterKnife.bind(this, stepView);
        inDualPane = getArguments().getBoolean(KitchenUtils.EXTRA_IN_DUAL_PANE, false);
        setRetainInstance(true);
        return stepView;
    }

    private void loadViews(Steps step) {
        if (step != null) {
            populateUi(step);
            setUpPlayer(Uri.parse(step.getVideoURL()));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mStep = getArguments().getParcelable(KitchenUtils.EXTRA_STEP);
        } else {
            mStep = savedInstanceState.getParcelable(KitchenUtils.EXTRA_STEP);
        }
        loadViews(mStep);
        checkForOrientation(getResources().getConfiguration().orientation);
    }

    private void populateUi(Steps step) {
        if (TextUtils.isEmpty(step.getVideoURL())) {
            mPlayerView.setVisibility(View.GONE);
            mThumbnail.setVisibility(View.VISIBLE);
            int recipeImageId = KitchenUtils.getImageIdWithName("");
            if (TextUtils.isEmpty(step.getThumbnailURL())) {
                Picasso.with(getContext()).load(recipeImageId).placeholder(recipeImageId).into(mThumbnail);
            } else {
                Picasso.with(getContext()).load(step.getThumbnailURL()).placeholder(recipeImageId).into(mThumbnail);
            }

        } else {
            mPlayerView.setVisibility(View.VISIBLE);
            mThumbnail.setVisibility(View.GONE);
        }
        mStepDesc.setText(step.getDescription());
    }


    private void setUpPlayer(Uri uri) {
        if (mPlayer == null) {
            TrackSelector mTrackSelector = new DefaultTrackSelector();
            LoadControl mLoadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),
                    mTrackSelector, mLoadControl);
            mPlayerView.setPlayer(mPlayer);
            mPlayer.addListener(new SimplePlayerEventListener());
            mPlayer.setPlayWhenReady(playWhenReady);
            mPlayer.seekTo(windowIndex, playbackPosition);

        }
        mMediaSource = buildMediaSource(uri);
        mPlayer.prepare(mMediaSource);

    }

    private MediaSource buildMediaSource(Uri uri) {
        mMediaSource = new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory(getUserAgent()), new DefaultExtractorsFactory(),
                null, null);
        return mMediaSource;
    }

    private String getUserAgent() {
        return Util.getUserAgent(getContext(), getString(R.string.app_name));
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            setUpPlayer(Uri.parse(mStep.getVideoURL()));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KitchenUtils.EXTRA_STEP, mStep);
        outState.putLong("playbackPosition", playbackPosition);
        outState.putInt("windowIndex", windowIndex);
        outState.putBoolean("playWhenReady", playWhenReady);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(KitchenUtils.EXTRA_STEP);
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            windowIndex = savedInstanceState.getInt("windowIndex");
        }

        Log.e("The Player Status", "" + playbackPosition + playWhenReady + windowIndex);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            playbackPosition = mPlayer.getCurrentPosition();
            windowIndex = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.removeListener(new SimplePlayerEventListener());
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void checkForOrientation(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!TextUtils.isEmpty(mStep.getVideoURL())) {
                if (!inDualPane) {
                    mLayout.setVisibility(View.GONE);
                    mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
                } else {
                    mLayout.setVisibility(View.VISIBLE);
                }
            } else {
                mLayout.setVisibility(View.VISIBLE);
            }
        } else {
            mLayout.setVisibility(View.VISIBLE);
        }
    }

    private class SimplePlayerEventListener implements ExoPlayer.EventListener {

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case ExoPlayer.STATE_READY:
                    mPlayerView.setVisibility(View.VISIBLE);
                    break;
                case ExoPlayer.STATE_ENDED:
                    mPlayer.setPlayWhenReady(false);
                    mPlayer.seekToDefaultPosition();
                    break;

            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity() {

        }
    }

}
