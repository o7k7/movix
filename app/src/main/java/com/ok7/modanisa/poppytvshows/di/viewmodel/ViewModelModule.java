package com.ok7.modanisa.poppytvshows.di.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ok7.modanisa.poppytvshows.screens.PopularTvShowsViewModel;
import com.ok7.modanisa.poppytvshows.ViewModelFactory;
import com.ok7.modanisa.poppytvshows.service.request.TvShowsRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import io.reactivex.disposables.CompositeDisposable;


@Module
public class ViewModelModule {
    /**
     * Each view model will be stored in a map with ViewModelKey which is its ".class" name
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    MutableLiveData mutableLiveData() {
        return new MutableLiveData<>();
    }

    @Provides
    ViewModelFactory viewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

    @Provides
    @IntoMap
    @ViewModelKey(PopularTvShowsViewModel.class)
    ViewModel getPopularTvShowsViewModule(TvShowsRepository tvShowsRepository, CompositeDisposable compositeDisposable, Provider<MutableLiveData> mutableLiveDataProvider) {
        return new PopularTvShowsViewModel(tvShowsRepository, compositeDisposable, mutableLiveDataProvider);
    }
}