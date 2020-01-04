package com.ok7.modanisa.poppytvshows.di.networking;

import android.content.Context;

import com.ok7.modanisa.poppytvshows.BuildConfig;
import com.ok7.modanisa.poppytvshows.common.database.DatabaseOperationUseCases;
import com.ok7.modanisa.poppytvshows.service.ConnectionControlInterceptor;
import com.ok7.modanisa.poppytvshows.service.ConnectionController;
import com.ok7.modanisa.poppytvshows.service.ServiceConstants;
import com.ok7.modanisa.poppytvshows.service.TvShowsApi;
import com.ok7.modanisa.poppytvshows.service.request.TvShowsRepository;

import javax.inject.Named;
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

    @Provides
    @Singleton
    ConnectionController getConnectionController() {
        return new ConnectionController();
    }

    @Singleton
    @Provides
    ConnectionControlInterceptor getConnectionControlInterceptor(@Named("ApplicationContext") Context context, ConnectionController connectionController) {
        return new ConnectionControlInterceptor(context, connectionController);
    }

    @Singleton
    @Provides
    Retrofit getRetrofit(ConnectionControlInterceptor interceptor) {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);
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
                .baseUrl(ServiceConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Provides
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Singleton
    @Provides
    TvShowsApi getMovieApi(Retrofit retrofit) {
        return retrofit.create(TvShowsApi.class);
    }

    @Provides
    TvShowsRepository getMoviesRepository(TvShowsApi movieApi,
                                          CompositeDisposable compositeDisposable,
                                          DatabaseOperationUseCases databaseOperationUseCases) {
        return new TvShowsRepository(compositeDisposable, movieApi, databaseOperationUseCases);
    }
}
