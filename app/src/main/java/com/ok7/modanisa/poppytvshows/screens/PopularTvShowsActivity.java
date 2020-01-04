package com.ok7.modanisa.poppytvshows.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.like.LikeButton;
import com.ok7.modanisa.poppytvshows.BR;
import com.ok7.modanisa.poppytvshows.common.BindingUtils;
import com.ok7.modanisa.poppytvshows.R;
import com.ok7.modanisa.poppytvshows.ViewModelFactory;
import com.ok7.modanisa.poppytvshows.databinding.ActivityPopularTvShowsBinding;
import com.ok7.modanisa.poppytvshows.model.Result;
import com.ok7.modanisa.poppytvshows.screens.common.BaseActivity;
import com.ok7.modanisa.poppytvshows.screens.tvshowdetail.TvShowDetailActivity;

import javax.inject.Inject;

public final class PopularTvShowsActivity extends BaseActivity<ActivityPopularTvShowsBinding, PopularTvShowsViewModel>
        implements BindingUtils.NextPage, PopularTvShowsAdapter.DetailedClickListener {

    @Inject
    ViewModelFactory mViewModelFactory;

    private static int page = 1;

    private PopularTvShowsViewModel mViewModel;

    private ActivityPopularTvShowsBinding mBindings;

    private Result clickedTvShow;

    private String adapterType;
    private Object adapter;

    private int possibleLikedItemIndex = 0;
    private LikeButton likeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PopularTvShowsAdapter popularTvShowsAdapter = new PopularTvShowsAdapter(this);
        getPresentationComponent().inject(this);
        super.onCreate(savedInstanceState);
        mBindings = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_popular_tv_shows, null, false);
        mBindings.setOnLoadMoreCallback(this);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        mBindings.rvPopularTvShows.setLayoutManager(gridLayoutManager);
        mBindings.rvPopularTvShows.setAdapter(popularTvShowsAdapter);
        setContentView(mBindings.getRoot());
        mBindings.setLifecycleOwner(this);
        mBindings.setVariable(BR.vmPopularTvShows, mViewModel);
        mViewModel.getPopularTvShows(message -> {
        });
    }

    @Override
    public int getBindingVariable() {
        return BR.vmPopularTvShows;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_popular_tv_shows;
    }

    @Override
    public PopularTvShowsViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(PopularTvShowsViewModel.class);
        return mViewModel;
    }

    @Override
    public void onLoadMore() {
        page++;
        mViewModel.getPopularTvShows(message -> {

        }, page);
    }

    @Override
    public void onClick(Result clickedTvShow, int index, String adapterType, Object adapter, LikeButton likeButton) {
        this.clickedTvShow = clickedTvShow;
        this.adapterType = adapterType;
        this.adapter = adapter;
        this.possibleLikedItemIndex = index;
        this.likeButton = likeButton;
        final Intent intent = new Intent(this, TvShowDetailActivity.class);
        intent.putExtra("TV_SHOW_ID", clickedTvShow.getId());
        startActivityForResult(intent, 1000);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                final Boolean isLiked = data.getBooleanExtra("IS_LIKED", false);
                if (adapter != null) {
                    clickedTvShow.setFavourite(isLiked);
                    if (TextUtils.equals(adapterType, "VERTICAL") || TextUtils.equals(adapterType, "HORIZONTAL")) {
                        ((RecyclerView.Adapter) adapter).notifyItemChanged(possibleLikedItemIndex, clickedTvShow);
                    } else {
                        if (likeButton != null) {
                            likeButton.setLiked(isLiked);
                        }
                    }
                }
            }
        }
    }
}
