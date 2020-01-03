package com.ok7.modanisa.poppytvshows.service.request;

import androidx.annotation.NonNull;

import com.ok7.modanisa.poppytvshows.model.TvShowDetail;
import com.ok7.modanisa.poppytvshows.model.TvShows;
import com.ok7.modanisa.poppytvshows.service.TvShowsApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public final class TvShowsRepository extends BaseRepository {

    //<editor-fold desc="Callbacks">
    @FunctionalInterface
    public interface OnSuccessPopularShows {
        void accept(TvShows tvShows);
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
                        .subscribe(onSuccessPopularShows::accept, throwable -> onFailurePopularShows.accept(handleErrorr(throwable).getStatusMessage()))
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
