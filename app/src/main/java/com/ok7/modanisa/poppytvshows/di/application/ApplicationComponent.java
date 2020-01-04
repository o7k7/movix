package com.ok7.modanisa.poppytvshows.di.application;

import com.ok7.modanisa.poppytvshows.di.database.DatabaseModule;
import com.ok7.modanisa.poppytvshows.di.networking.NetworkingModule;
import com.ok7.modanisa.poppytvshows.di.presentation.PresentationComponent;
import com.ok7.modanisa.poppytvshows.di.presentation.PresentationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkingModule.class,
        DatabaseModule.class
})
public interface ApplicationComponent {
    PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
