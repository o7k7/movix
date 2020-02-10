package com.ok7.mvvm.poppytvshows.common.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ok7.mvvm.poppytvshows.model.Result;

@Database(entities = {
        Result.class,
}, version = TvShowDatabase.VERSION, exportSchema = false)
@TypeConverters({DataTypeConverter.class})
public abstract class TvShowDatabase extends RoomDatabase {

    public static final String DB_NAME = "tv-show-db";

    static final int VERSION = 1;

    public abstract TvShowDao getTvShowDao();

}