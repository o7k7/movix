package com.ok7.mvvm.poppytvshows.screens.tvshowdetail;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.ok7.mvvm.poppytvshows.BR;
import com.ok7.mvvm.poppytvshows.R;
import com.ok7.mvvm.poppytvshows.ViewModelFactory;
import com.ok7.mvvm.poppytvshows.databinding.ActivityTvShowDetailBinding;
import com.ok7.mvvm.poppytvshows.model.Result;
import com.ok7.mvvm.poppytvshows.screens.common.BaseActivity;

import javax.inject.Inject;

public final class TvShowDetailActivity extends BaseActivity<ActivityTvShowDetailBinding, TvShowDetailViewModel> implements OnLikeListener {

    @Inject
    ViewModelFactory mViewModelFactory;

    private int tvShowId;

    private boolean isLiked = false;

    private TvShowDetailViewModel mViewModel;

    private Result tvShow;

    private LikeButton likeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.getPresentationComponent().inject(this);
        super.onCreate(savedInstanceState);
        tvShowId = getIntent().getIntExtra("TV_SHOW_ID", 0);
        ActivityTvShowDetailBinding mBindings = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_tv_show_detail, null, false);
        setContentView(mBindings.getRoot());
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBindings.setLifecycleOwner(this);
        mBindings.setVariable(BR.tvShowDetailVM, mViewModel);
        init();
        registerHandlers();
        prepareLikeButton();
        mViewModel.getTvShowDetails(String.valueOf(tvShowId));
    }

    private void init() {
        likeButton = findViewById(R.id.star_button);
    }

    private void registerHandlers() {

    }

    private void prepareLikeButton() {
        mViewModel.getSelectedTvShow(tvShowId, results -> {
            final Result tvShow = results.get(0);
            this.tvShow = tvShow;
            if (tvShow.isFavourite == null) {
                isLiked = false;
                likeButton.setLiked(false);
            } else {
                isLiked = tvShow.isFavourite;
                likeButton.setLiked(tvShow.isFavourite);
            }
        });
        likeButton.setOnLikeListener(this);
    }

    @Override
    public void liked(LikeButton likeButton) {
        isLiked = true;
        mViewModel.favouriteChange(tvShow, true);
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        isLiked = false;
        mViewModel.favouriteChange(tvShow, false);
    }

    @Override
    public int getBindingVariable() {
        return BR.tvShowDetailVM;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tv_show_detail;
    }

    @Override
    public TvShowDetailViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(TvShowDetailViewModel.class);
        return mViewModel;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("IS_LIKED", isLiked);
        setResult(RESULT_OK, intent);
        finish();
    }
}
