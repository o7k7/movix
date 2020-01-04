package com.ok7.modanisa.poppytvshows.common.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ok7.modanisa.poppytvshows.model.Result;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCustomers(List<Result> results);

    @Query("SELECT * from popular_shows ORDER BY createDate ASC LIMIT :limit OFFSET :offset")
    Single<List<Result>> getPopularTvShows(int limit, int offset);

    @Query("DELETE FROM popular_shows")
    void clearTable();

    @Update
    void updateCustomerSearchHistory(Result result);
}
