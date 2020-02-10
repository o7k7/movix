package com.ok7.mvvm.poppytvshows.common.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ok7.mvvm.poppytvshows.model.Result;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCustomers(List<Result> results);

    @Query("SELECT * from popular_shows ORDER BY createDate ASC LIMIT :limit OFFSET :offset")
    Single<List<Result>> getPopularTvShows(int limit, int offset);

    @Query("SELECT * from popular_shows WHERE id = :id")
    Single<List<Result>> getSelectedTvShow(Integer id);

    @Query("DELETE FROM popular_shows")
    void clearTable();

    @Query("UPDATE popular_shows SET favourite = :isFavourite WHERE id = :id ")
    void updateSelectedCustomer(int id, boolean isFavourite);

    @Update
    void updateCustomerSearchHistory(Result result);
}
