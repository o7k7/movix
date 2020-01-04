package com.ok7.modanisa.poppytvshows.screens;

import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ok7.modanisa.poppytvshows.BR;
import com.ok7.modanisa.poppytvshows.common.BindingUtils;
import com.ok7.modanisa.poppytvshows.R;
import com.ok7.modanisa.poppytvshows.ViewModelFactory;
import com.ok7.modanisa.poppytvshows.databinding.ActivityPopularTvShowsBinding;
import com.ok7.modanisa.poppytvshows.screens.common.BaseActivity;

import javax.inject.Inject;

public final class PopularTvShowsActivity extends BaseActivity<ActivityPopularTvShowsBinding, PopularTvShowsViewModel> implements BindingUtils.NextPage {

    @Inject
    ViewModelFactory mViewModelFactory;

    private static int page = 1;

    private PopularTvShowsViewModel mViewModel;

    private ActivityPopularTvShowsBinding mBindings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PopularTvShowsAdapter popularTvShowsAdapter = new PopularTvShowsAdapter();
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
        Log.e("TEST:", String.valueOf(page) + " PAGE");
        mViewModel.getPopularTvShows(message -> {

        }, page);
    }
}
