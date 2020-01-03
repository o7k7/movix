package com.ok7.modanisa.poppytvshows.di.networking;

import com.ok7.modanisa.poppytvshows.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {

    @Singleton
    @Provides
    Retrofit getRetrofit() {

        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor.Level logLevel;
        if (BuildConfig.DEBUG) {
            logLevel = HttpLoggingInterceptor.Level.BODY;
        } else {
            logLevel = HttpLoggingInterceptor.Level.NONE;
        }
        logging.setLevel(logLevel);
        httpClient.addInterceptor(logging);
        return new Retrofit
                .Builder()
                .baseUrl("https://developers.themoviedb.org/3/tv/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Provides
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }
}
