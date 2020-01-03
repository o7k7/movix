package com.ok7.modanisa.poppytvshows;

import android.app.Application;

import com.ok7.modanisa.poppytvshows.di.application.ApplicationComponent;
import com.ok7.modanisa.poppytvshows.di.application.ApplicationModule;
import com.ok7.modanisa.poppytvshows.di.application.DaggerApplicationComponent;
import com.ok7.modanisa.poppytvshows.di.networking.NetworkingModule;


public final class PoppyApp extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .networkingModule(new NetworkingModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
