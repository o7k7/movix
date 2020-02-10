package com.ok7.mvvm.poppytvshows.di.presentation;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

@Module
public class PresentationModule {

    private final FragmentActivity mActivity;

    public PresentationModule(FragmentActivity activity) {
        mActivity = activity;
    }

    @Provides
    Context getContext() {
        return mActivity;
    }

    @Provides
    LayoutInflater getLayoutInflater() {
        return mActivity.getLayoutInflater();
    }

    @Provides
    FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }
}
