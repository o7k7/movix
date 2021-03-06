package com.ok7.mvvm.poppytvshows.di.service;

import android.app.Service;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    Service service() {
        return mService;
    }

    @Provides
    Context context(Service service) {
        return service;
    }

}
