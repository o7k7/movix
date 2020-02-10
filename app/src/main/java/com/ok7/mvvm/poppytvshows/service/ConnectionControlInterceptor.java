package com.ok7.mvvm.poppytvshows.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class ConnectionControlInterceptor implements Interceptor {

    private final Context context;
    private final ConnectionController connectionController;

    public ConnectionControlInterceptor(Context context, ConnectionController connectionController) {
        this.context = context;
        this.connectionController = connectionController;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request original = chain.request();
        final HttpUrl originalHttpUrl = original.url();

        final HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", ServiceConstants.API_KEY)
                .build();
        final Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        final Request request = requestBuilder.build();
        if (!isConnected(context)) {
            connectionController.publish();
        } else {
            return chain.proceed(request);
        }
        return null;
    }

    private static boolean isConnected(Context context) {
        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo connection = manager.getActiveNetworkInfo();
        if (connection != null && connection.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
