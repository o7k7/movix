package com.ok7.modanisa.poppytvshows.common.database;

import com.ok7.modanisa.poppytvshows.model.Result;

import java.util.List;

import io.reactivex.Single;

public interface PopularTvShowsDataSource {
    void insertToPopularTvShows(List<Result> results);

    Single<List<Result>> getPopularTvShows(int limit, int offset);

    Single<List<Result>> getSelectedTvShow(Integer id);

    void clearTable();

    void updateCustomerSearchHistory(Result result);

}
