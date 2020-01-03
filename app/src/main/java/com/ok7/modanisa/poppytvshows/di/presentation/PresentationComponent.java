package com.ok7.modanisa.poppytvshows.di.presentation;


import com.ok7.modanisa.poppytvshows.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = PresentationModule.class)
public interface PresentationComponent {

    void inject(MainActivity mainActivity);
}
