package com.ok7.modanisa.poppytvshows;

import androidx.lifecycle.MutableLiveData;

import com.ok7.modanisa.poppytvshows.model.Result;
import com.ok7.modanisa.poppytvshows.service.request.TvShowsRepository;

import java.util.List;

import javax.inject.Provider;

import io.reactivex.disposables.CompositeDisposable;

public final class PopularTvShowsViewModel extends BaseViewModel {

    private TvShowsRepository mTvShowsRepository;

    private CompositeDisposable mCompositeDisposable;

    public MutableLiveData<List<Result>> mPopularTvShows;

    public PopularTvShowsViewModel(TvShowsRepository tvShowsRepository,
                                   CompositeDisposable compositeDisposable,
                                   Provider<MutableLiveData> mutableLiveData) {
        this.mTvShowsRepository = tvShowsRepository;
        this.mCompositeDisposable = compositeDisposable;
        this.mPopularTvShows = mutableLiveData.get();
    }

    void getPopularTvShows(MessageCallback messageCallback) {
        mTvShowsRepository.getPopularTvShows(1,
                tvShows -> mPopularTvShows.setValue(tvShows.getResults()),
                messageCallback::onMessageReceived
        );
    }

    @Override
    protected void onCleared() {
        this.mPopularTvShows.getValue().clear();
        super.onCleared();
    }
}
