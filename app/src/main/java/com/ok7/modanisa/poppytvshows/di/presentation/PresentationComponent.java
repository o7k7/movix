package com.ok7.modanisa.poppytvshows.di.presentation;


import com.ok7.modanisa.poppytvshows.di.viewmodel.ViewModelModule;
import com.ok7.modanisa.poppytvshows.screens.PopularTvShowsActivity;
import com.ok7.modanisa.poppytvshows.screens.tvshowdetail.TvShowDetailActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {PresentationModule.class, ViewModelModule.class})
public interface PresentationComponent {

    void inject(PopularTvShowsActivity popularTvShowsActivity);

    void inject(TvShowDetailActivity tvShowDetailActivity);
}
