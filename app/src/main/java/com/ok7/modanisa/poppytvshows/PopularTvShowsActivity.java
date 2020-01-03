package com.ok7.modanisa.poppytvshows;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.ok7.modanisa.poppytvshows.databinding.ActivityPopularTvShowsBinding;

import javax.inject.Inject;

public final class PopularTvShowsActivity extends BaseActivity<ActivityPopularTvShowsBinding, PopularTvShowsViewModel> {

    @Inject
    ViewModelFactory mViewModelFactory;

    private PopularTvShowsViewModel mViewModel;

    private ActivityPopularTvShowsBinding mBindings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getPresentationComponent().inject(this);
        super.onCreate(savedInstanceState);
        mBindings = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_popular_tv_shows, null, false);
        setContentView(mBindings.getRoot());
    }

    @Override
    public int getBindingVariable() {
        return 0;
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

}
