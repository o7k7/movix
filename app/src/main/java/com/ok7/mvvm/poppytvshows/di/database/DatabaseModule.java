package com.ok7.mvvm.poppytvshows.di.database;

import android.app.Application;

import androidx.room.Room;

import com.ok7.mvvm.poppytvshows.common.database.DatabaseOperationUseCases;
import com.ok7.mvvm.poppytvshows.common.database.PopularTvShowsDataSourceImpl;
import com.ok7.mvvm.poppytvshows.common.database.TvShowDao;
import com.ok7.mvvm.poppytvshows.common.database.TvShowDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class DatabaseModule {
    private final TvShowDatabase mTvShowDatabase;

    public DatabaseModule(Application mApplication) {
        mTvShowDatabase = Room.databaseBuilder(mApplication,
                TvShowDatabase.class, TvShowDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    PopularTvShowsDataSourceImpl customerRepository(TvShowDao tvShowDao) {
        return new PopularTvShowsDataSourceImpl(tvShowDao);
    }

    @Singleton
    @Provides
    TvShowDatabase getTvShowDatabase() {
        return mTvShowDatabase;
    }

    @Singleton
    @Provides
    TvShowDao getCustomerDao(TvShowDatabase tvShowDatabase) {
        return tvShowDatabase.getTvShowDao();
    }

    @Provides
    DatabaseOperationUseCases getDatabaseOperationUseCases(PopularTvShowsDataSourceImpl popularTvShowsDataSource, CompositeDisposable compositeDisposable) {
        return new DatabaseOperationUseCases(popularTvShowsDataSource, compositeDisposable);
    }
}