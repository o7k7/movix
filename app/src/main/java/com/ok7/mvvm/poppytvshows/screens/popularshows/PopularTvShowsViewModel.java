package com.ok7.mvvm.poppytvshows.screens.popularshows;

import androidx.lifecycle.MutableLiveData;

import com.ok7.mvvm.poppytvshows.common.Util;
import com.ok7.mvvm.poppytvshows.model.PopularShowSections;
import com.ok7.mvvm.poppytvshows.model.Result;
import com.ok7.mvvm.poppytvshows.screens.common.BaseViewModel;
import com.ok7.mvvm.poppytvshows.service.request.TvShowsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

import io.reactivex.disposables.CompositeDisposable;

public final class PopularTvShowsViewModel extends BaseViewModel {

    private TvShowsRepository mTvShowsRepository;

    private CompositeDisposable mCompositeDisposable;

    public MutableLiveData<List<PopularShowSections>> mPopularTvShows;

    public MutableLiveData<Boolean> isDataChanged;

    public PopularTvShowsViewModel(TvShowsRepository tvShowsRepository,
                                   CompositeDisposable compositeDisposable,
                                   Provider<MutableLiveData> mutableLiveData) {
        this.mTvShowsRepository = tvShowsRepository;
        this.mCompositeDisposable = compositeDisposable;
        this.mPopularTvShows = mutableLiveData.get();
        this.isDataChanged = new MutableLiveData<>(false);
    }

    void getPopularTvShows(boolean isUpdateCheck, MessageCallback messageCallback) {
        mTvShowsRepository.getPopularTvShows(1,
                tvShows -> {
                    if (!isUpdateCheck) {
                        mPopularTvShows.setValue(tvShows);
                    } else {
                        final List<Result> obtainedList = new ArrayList<>();
                        final List<Result> newlyObtainedList = new ArrayList<>();
                        final List<PopularShowSections> value = mPopularTvShows.getValue();
                        for (PopularShowSections showSections : tvShows) {
                            newlyObtainedList.addAll(showSections.getTvShows());
                        }
                        if (value != null) {
                            for (PopularShowSections showSections : value) {
                                obtainedList.addAll(showSections.getTvShows());
                            }
                            if (obtainedList.size() >= 20 && Util.listEqualsIgnoreOrder(obtainedList, newlyObtainedList)) {
                                isDataChanged.setValue(true);
                            } else {
                                isDataChanged.setValue(false);
                            }
                        }
                    }
                },
                messageCallback::onMessageReceived
        );
    }

    void clearToRefresh() {
        mPopularTvShows.setValue(null);
    }

    void getPopularTvShows(MessageCallback messageCallback, int page) {
        mTvShowsRepository.getPopularTvShows(page,
                tvShows -> {
                    List<PopularShowSections> value = mPopularTvShows.getValue();
                    value.addAll(tvShows);
                    mPopularTvShows.setValue(value);
                },
                messageCallback::onMessageReceived
        );
    }

    @Override
    protected void onCleared() {
        this.mPopularTvShows.getValue().clear();
        super.onCleared();
    }
}
