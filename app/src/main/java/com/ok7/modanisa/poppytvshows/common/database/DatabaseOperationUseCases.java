package com.ok7.modanisa.poppytvshows.common.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ok7.modanisa.poppytvshows.model.Result;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public final class DatabaseOperationUseCases {

    @FunctionalInterface
    public interface Insert {
        void onComplete(boolean withErrors);
    }

    @FunctionalInterface
    public interface Select {
        void onComplete(@Nullable List<Result> results);
    }

    private final CompositeDisposable mCompositeDisposable;

    private final PopularTvShowsDataSourceImpl mPopularTvShowsDataSource;

    public DatabaseOperationUseCases(PopularTvShowsDataSourceImpl popularTvShowsDataSource,
                                     CompositeDisposable compositeDisposable) {
        this.mPopularTvShowsDataSource = popularTvShowsDataSource;
        this.mCompositeDisposable = compositeDisposable;
    }

    public void getPopularTvShows(int limit, int offset, Select select) {
        mCompositeDisposable.add(
                mPopularTvShowsDataSource.getPopularTvShows(limit, offset)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(select::onComplete, throwable -> {
                            select.onComplete(null);
                        })
        );
    }

    public void getPopularTvShows(Integer id, Select select) {
        mCompositeDisposable.add(
                mPopularTvShowsDataSource.getSelectedTvShow(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(select::onComplete, throwable -> {
                            select.onComplete(null);
                        })
        );
    }

    public void addPopularTvShows(List<Result> results, Insert insert) {
        final Calendar calendar = Calendar.getInstance();
        for (Result result : results) {
            calendar.add(Calendar.MILLISECOND, 1);
            result.setCreateDate(calendar.getTime());
        }
        final CompletableObserver completableObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                insert.onComplete(false);
            }

            @Override
            public void onError(Throwable e) {
                insert.onComplete(true);
            }
        };
        Completable.fromAction(() -> mPopularTvShowsDataSource.insertToPopularTvShows(results))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(completableObserver);
    }

    public void updateSelectedCustomer(@NonNull Result tvShow) {
        final CompletableObserver completableObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        };
        Completable.fromAction(() -> mPopularTvShowsDataSource.updateCustomerSearchHistory(tvShow))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(completableObserver);
    }

    public void clearTable() {
        Completable.fromAction(mPopularTvShowsDataSource::clearTable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void dispose() {
        mCompositeDisposable.dispose();
    }
}
