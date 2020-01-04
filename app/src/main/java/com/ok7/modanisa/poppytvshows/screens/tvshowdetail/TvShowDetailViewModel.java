package com.ok7.modanisa.poppytvshows.screens.tvshowdetail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ok7.modanisa.poppytvshows.common.Util;
import com.ok7.modanisa.poppytvshows.common.database.DatabaseOperationUseCases;
import com.ok7.modanisa.poppytvshows.model.Genre;
import com.ok7.modanisa.poppytvshows.model.Result;
import com.ok7.modanisa.poppytvshows.model.TvShowDetail;
import com.ok7.modanisa.poppytvshows.screens.common.BaseViewModel;
import com.ok7.modanisa.poppytvshows.service.request.TvShowsRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class TvShowDetailViewModel extends BaseViewModel {

    private DatabaseOperationUseCases databaseOperations;

    private TvShowsRepository tvShowsRepository;

    private CompositeDisposable compositeDisposable;

    public MutableLiveData<TvShowDetail> mTvShowDetail = new MutableLiveData<>();

    public MutableLiveData<List<Genre>> mGenres = new MutableLiveData<>();

    public MutableLiveData<String> mSeasonCount = new MutableLiveData<>();

    public MutableLiveData<String> mEpisodeCount = new MutableLiveData<>();

    public TvShowDetailViewModel(DatabaseOperationUseCases databaseOperations, TvShowsRepository tvShowsRepository, CompositeDisposable compositeDisposable) {
        this.databaseOperations = databaseOperations;
        this.tvShowsRepository = tvShowsRepository;
        this.compositeDisposable = compositeDisposable;
    }

    void getTvShowDetails(String id) {
        tvShowsRepository.getTvShowDetails(id, tvShows -> {
            getGenreList(tvShows.getGenres());
            mTvShowDetail.setValue(tvShows);
            if (tvShows.getSeasons() != null) {
                mSeasonCount.setValue(String.valueOf(tvShows.getSeasons().size()));
            }
            if (tvShows.getNumberOfEpisodes() != null) {
                mEpisodeCount.setValue(String.valueOf(tvShows.getNumberOfEpisodes()));
            }
        }, message -> {
            Log.e("TEST::", message);
        });
    }

    void getGenreList(List<Genre> tvShowsGenre) {
        tvShowsRepository.getGenreList(allGenres -> {
            final List<Genre> genres = allGenres.getGenres();
            List<Genre> intersection = Util.intersection(genres, tvShowsGenre);
            mGenres.setValue(new ArrayList<>(intersection));
        }, message -> {

        });
    }

    void favouriteChange(Result tvShow, Boolean isFavourite) {
        tvShow.setFavourite(isFavourite);
        databaseOperations.updateSelectedCustomer(tvShow);
    }

    void getSelectedTvShow(int id, DatabaseOperationUseCases.Select select) {
        databaseOperations.getPopularTvShows(id, select);
    }
}
