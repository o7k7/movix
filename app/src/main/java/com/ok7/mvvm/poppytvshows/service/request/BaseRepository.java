package com.ok7.mvvm.poppytvshows.service.request;

import com.google.gson.Gson;
import com.ok7.mvvm.poppytvshows.common.Constants;
import com.ok7.mvvm.poppytvshows.model.Error;

import java.io.IOException;
import java.net.HttpURLConnection;

import retrofit2.HttpException;

public abstract class BaseRepository {

    protected Error handleErrorr(Throwable throwable) {
        final Gson gson = new Gson();
        if (throwable instanceof HttpException) {
            final HttpException httpException = (HttpException) throwable;
            if (httpException.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                Error error;
                try {
                    if (httpException.response().errorBody() != null) {
                        error = gson.fromJson(httpException.response().errorBody().string(), Error.class);
                    } else {
                        error = new Error(Constants.GENERAL_ERROR);
                    }
                } catch (IOException e) {
                    return new Error(Constants.GENERAL_ERROR);
                }
                return error;
            }
        }
        return new Error(Constants.GENERAL_ERROR);
    }
}
