package com.ok7.modanisa.poppytvshows.service.request;

import androidx.annotation.NonNull;

import com.ok7.modanisa.poppytvshows.model.PopularShowSections;
import com.ok7.modanisa.poppytvshows.model.PopularTvShowsViewTypes;
import com.ok7.modanisa.poppytvshows.model.TvShowDetail;
import com.ok7.modanisa.poppytvshows.service.TvShowsApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public final class TvShowsRepository extends BaseRepository {

    private final static short MAX_ELEMENTS_IN_PAGE = 20;

    private final static short HALF_OF_MAX_ELEMENTS_IN_PAGE = MAX_ELEMENTS_IN_PAGE / 2;

    //<editor-fold desc="Callbacks">
    @FunctionalInterface
    public interface OnSuccessPopularShows {
        void accept(List<PopularShowSections> tvShows);
    }

    @FunctionalInterface
    public interface OnFailurePopularShows {
        void accept(String message);
    }

    @FunctionalInterface
    public interface OnSuccessShowDetail {
        void accept(TvShowDetail tvShows);
    }

    @FunctionalInterface
    public interface OnFailureShowDetail {
        void accept(String message);
    }
    //</editor-fold>

    private final CompositeDisposable mCompositeDisposable;

    private final TvShowsApi mTvShowsApi;

    public TvShowsRepository(CompositeDisposable compositeDisposable, TvShowsApi tvShowsApi) {
        this.mCompositeDisposable = compositeDisposable;
        this.mTvShowsApi = tvShowsApi;
    }

    /**
     * @param page
     * @param onSuccessPopularShows
     * @param onFailurePopularShows
     */
    public void getPopularTvShows(int page, OnSuccessPopularShows onSuccessPopularShows, OnFailurePopularShows onFailurePopularShows) {
        mCompositeDisposable.add(
                mTvShowsApi.getPopularTvShows(String.valueOf(page))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(tvShows -> {
                            if (page == 1 && tvShows.getResults().size() == MAX_ELEMENTS_IN_PAGE) {
                                final List<PopularShowSections> popularShowSections = new ArrayList<>();
                                popularShowSections.add(new PopularShowSections(tvShows.getResults().subList(0, HALF_OF_MAX_ELEMENTS_IN_PAGE), PopularTvShowsViewTypes.PAGER));
                                popularShowSections.add(new PopularShowSections(tvShows.getResults().subList(HALF_OF_MAX_ELEMENTS_IN_PAGE, tvShows.getResults().size()), PopularTvShowsViewTypes.GRID));
                                onSuccessPopularShows.accept(popularShowSections);
                            } else if (page == 2 && tvShows.getResults().size() == MAX_ELEMENTS_IN_PAGE) {
                                final List<PopularShowSections> popularShowSections = new ArrayList<>();
                                popularShowSections.add(new PopularShowSections(tvShows.getResults().subList(0, HALF_OF_MAX_ELEMENTS_IN_PAGE), PopularTvShowsViewTypes.GRID));
                                popularShowSections.add(new PopularShowSections(tvShows.getResults().subList(HALF_OF_MAX_ELEMENTS_IN_PAGE, tvShows.getResults().size()), PopularTvShowsViewTypes.GRID));
                                onSuccessPopularShows.accept(popularShowSections);
                            } else {
                                final List<PopularShowSections> popularShowSections = new ArrayList<>();
                                popularShowSections.add(new PopularShowSections(tvShows.getResults(), PopularTvShowsViewTypes.GRID));
                                onSuccessPopularShows.accept(popularShowSections);
                            }
                        }, throwable -> onFailurePopularShows.accept(handleErrorr(throwable).getStatusMessage()))
        );
    }

    /**
     * @param tvShowId
     * @param onSuccessShowDetail
     * @param onFailureShowDetail
     */
    public void getTvShowDetails(@NonNull String tvShowId, OnSuccessShowDetail onSuccessShowDetail, OnFailureShowDetail onFailureShowDetail) {
        mCompositeDisposable.add(
                mTvShowsApi.getTvShowDetails(tvShowId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onSuccessShowDetail::accept, throwable -> onFailureShowDetail.accept(handleErrorr(throwable).getStatusMessage()))
        );
    }

    /**
     * To dispose completables
     */
    public void clearReferences() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
}
