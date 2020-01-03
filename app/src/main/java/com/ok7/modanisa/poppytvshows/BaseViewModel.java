package com.ok7.modanisa.poppytvshows;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    @FunctionalInterface
    interface MessageCallback {
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
