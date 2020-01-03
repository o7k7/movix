package com.ok7.modanisa.poppytvshows.di.application;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Named("ApplicationContext")
    Context getContext() {
        return mApplication.getApplicationContext();
    }
    @Provides
    @Singleton
    Application getApplication() {
        return mApplication;
    }
}
