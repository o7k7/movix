package com.ok7.modanisa.poppytvshows.common.database;

import com.ok7.modanisa.poppytvshows.model.Result;

import java.util.List;

import io.reactivex.Single;

public final class PopularTvShowsDataSourceImpl implements PopularTvShowsDataSource {

    private final TvShowDao mTvShowDao;

    public PopularTvShowsDataSourceImpl(TvShowDao tvShowDao) {
        this.mTvShowDao = tvShowDao;
    }

    @Override
    public void insertToPopularTvShows(List<Result> results) {
        mTvShowDao.insertCustomers(results);
    }

    @Override
    public Single<List<Result>> getPopularTvShows(int limit, int offset) {
        return mTvShowDao.getPopularTvShows(limit, offset);
    }

    @Override
    public void clearTable() {
        mTvShowDao.clearTable();
    }

    @Override
    public void updateCustomerSearchHistory(Result result) {
        mTvShowDao.updateCustomerSearchHistory(result);
    }
}
