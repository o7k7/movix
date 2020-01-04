package com.ok7.modanisa.poppytvshows.service.request;

import androidx.annotation.NonNull;

import com.ok7.modanisa.poppytvshows.common.database.DatabaseOperationUseCases;
import com.ok7.modanisa.poppytvshows.model.Genres;
import com.ok7.modanisa.poppytvshows.model.PopularShowSections;
import com.ok7.modanisa.poppytvshows.model.PopularTvShowsViewTypes;
import com.ok7.modanisa.poppytvshows.model.Result;
import com.ok7.modanisa.poppytvshows.model.TvShowDetail;
import com.ok7.modanisa.poppytvshows.model.TvShows;
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

    @FunctionalInterface
    public interface OnSuccessGenreList {
        void accept(Genres tvShows);
    }

    @FunctionalInterface
    public interface OnFailureGenreList {
        void accept(String message);
    }
    //</editor-fold>

    private final CompositeDisposable mCompositeDisposable;

    private final TvShowsApi mTvShowsApi;

    private final DatabaseOperationUseCases mDatabaseOperations;

    public TvShowsRepository(CompositeDisposable compositeDisposable,
                             TvShowsApi tvShowsApi,
                             DatabaseOperationUseCases databaseOperationUseCases) {
        this.mCompositeDisposable = compositeDisposable;
        this.mTvShowsApi = tvShowsApi;
        this.mDatabaseOperations = databaseOperationUseCases;
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
                            mDatabaseOperations.addPopularTvShows(tvShows.getResults(),
                                    withErrors -> {
                                        if (!withErrors) {
                                            mDatabaseOperations.getPopularTvShows(MAX_ELEMENTS_IN_PAGE, page == 1 ? 0 : (MAX_ELEMENTS_IN_PAGE * (page - 1)), results -> {
                                                createSections(page, results, onSuccessPopularShows);
                                            });
                                        }
                                    });
                        }, throwable -> {
                            onFailurePopularShows.accept(handleErrorr(throwable).getStatusMessage());
                        })
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

    public void getGenreList(OnSuccessGenreList onSuccessGenreList, OnFailureGenreList onFailureGenreList) {
        mCompositeDisposable.add(
            mTvShowsApi.getTvShowDetails()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(genres -> {
                        onSuccessGenreList.accept(genres);
                    }, throwable -> {
                    })
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

    private void createSections(int page, List<Result> results, OnSuccessPopularShows onSuccessPopularShows) {
        if (page == 1 && results.size() == MAX_ELEMENTS_IN_PAGE) {
            final List<PopularShowSections> popularShowSections = new ArrayList<>();
            popularShowSections.add(new PopularShowSections(results.subList(0, HALF_OF_MAX_ELEMENTS_IN_PAGE), PopularTvShowsViewTypes.PAGER));
            popularShowSections.add(new PopularShowSections(results.subList(HALF_OF_MAX_ELEMENTS_IN_PAGE, results.size()), PopularTvShowsViewTypes.GRID));
            onSuccessPopularShows.accept(popularShowSections);
        } else if (page == 2 && results.size() == MAX_ELEMENTS_IN_PAGE) {
            final List<PopularShowSections> popularShowSections = new ArrayList<>();
            popularShowSections.add(new PopularShowSections(results.subList(0, HALF_OF_MAX_ELEMENTS_IN_PAGE), PopularTvShowsViewTypes.HORIZONTAL));
            popularShowSections.add(new PopularShowSections(results.subList(HALF_OF_MAX_ELEMENTS_IN_PAGE, results.size()), PopularTvShowsViewTypes.GRID));
            onSuccessPopularShows.accept(popularShowSections);
        } else {
            final List<PopularShowSections> popularShowSections = new ArrayList<>();
            popularShowSections.add(new PopularShowSections(results, PopularTvShowsViewTypes.GRID));
            onSuccessPopularShows.accept(popularShowSections);
        }
    }
}
