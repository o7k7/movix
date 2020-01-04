package com.ok7.modanisa.poppytvshows.screens.tvshowdetail

import com.ok7.modanisa.poppytvshows.common.database.DatabaseOperationUseCases
import com.ok7.modanisa.poppytvshows.model.Result
import com.ok7.modanisa.poppytvshows.screens.common.BaseViewModel
import com.ok7.modanisa.poppytvshows.service.request.TvShowsRepository
import io.reactivex.disposables.CompositeDisposable

class TvShowDetailViewModel(databaseOperations: DatabaseOperationUseCases,
                            tvShowsRepository: TvShowsRepository,
                            compositeDisposable: CompositeDisposable) : BaseViewModel() {

    private val databaseOperations: DatabaseOperationUseCases = databaseOperations
    private val tvShowsRepository: TvShowsRepository = tvShowsRepository
    private val compositeDisposable: CompositeDisposable = compositeDisposable

    fun getTvShowDetails(id: String) {
        tvShowsRepository.getTvShowDetails(id, TvShowsRepository.OnSuccessShowDetail {

        }, TvShowsRepository.OnFailureShowDetail {

        })
    }

    fun favouriteChange(tvShow: Result, isFavourite: Boolean) {
        tvShow.favourite = isFavourite
        databaseOperations.updateSelectedCustomer(tvShow)
    }

    fun getSelectedTvShow(id: Int, select: DatabaseOperationUseCases.Select) {
        databaseOperations.getPopularTvShows(id, select)
    }
}