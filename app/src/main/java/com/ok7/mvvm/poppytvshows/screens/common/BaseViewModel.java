package com.ok7.mvvm.poppytvshows.screens.common;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    @FunctionalInterface
    public interface MessageCallback {
        void onMessageReceived(String message);
    }

    public final ObservableBoolean mIsLoading = new ObservableBoolean();

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }
}
