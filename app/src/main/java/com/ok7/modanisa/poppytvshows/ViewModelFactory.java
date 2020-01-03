package com.ok7.modanisa.poppytvshows;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Provider;

public final class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> mProviderMap;

    /**
     * With provider map<ClassName, ViewModel>
     *
     * @param providerMap
     */
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        this.mProviderMap = providerMap;
    }

    /**
     * Obtain from map with its class name
     *
     * @param modelClass
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) mProviderMap.get(modelClass).get();
    }
}