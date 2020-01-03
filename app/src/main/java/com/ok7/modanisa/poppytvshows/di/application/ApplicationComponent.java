package com.ok7.modanisa.poppytvshows.di.application;

import com.ok7.modanisa.poppytvshows.di.networking.NetworkingModule;
import com.ok7.modanisa.poppytvshows.di.presentation.PresentationComponent;
import com.ok7.modanisa.poppytvshows.di.presentation.PresentationModule;
import com.ok7.modanisa.poppytvshows.di.viewmodel.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {
    PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
