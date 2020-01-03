package com.ok7.modanisa.poppytvshows.di.presentation;


import com.ok7.modanisa.poppytvshows.PopularTvShowsActivity;
import com.ok7.modanisa.poppytvshows.di.viewmodel.ViewModelModule;

import dagger.Subcomponent;

@Subcomponent(modules = {PresentationModule.class, ViewModelModule.class})
public interface PresentationComponent {

    void inject(PopularTvShowsActivity popularTvShowsActivity);
}
