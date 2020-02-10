package com.ok7.mvvm.poppytvshows.service;


import com.ok7.mvvm.poppytvshows.model.Genres;
import com.ok7.mvvm.poppytvshows.model.TvShowDetail;
import com.ok7.mvvm.poppytvshows.model.TvShows;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowsApi {

    @GET(ServiceConstants.POPULAR_TV_SHOWS)
    Observable<TvShows> getPopularTvShows(@Query("page") final String page);

    @GET(ServiceConstants.TV_SHOWS_DETAIL + "{id}")
    Observable<TvShowDetail> getTvShowDetails(@Path("id") final String id);

    @GET(ServiceConstants.GENRE_LIST)
    Observable<Genres> getTvShowDetails();
}
