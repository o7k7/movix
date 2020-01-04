package com.ok7.modanisa.poppytvshows.screens.tvshowdetail

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.like.LikeButton
import com.like.OnLikeListener
import com.ok7.modanisa.poppytvshows.R
import com.ok7.modanisa.poppytvshows.ViewModelFactory
import com.ok7.modanisa.poppytvshows.common.database.DatabaseOperationUseCases
import com.ok7.modanisa.poppytvshows.databinding.ActivityTvShowDetailBinding
import com.ok7.modanisa.poppytvshows.model.Result
import com.ok7.modanisa.poppytvshows.screens.common.BaseActivity
import kotlinx.android.synthetic.main.activity_tv_show_detail.*
import javax.inject.Inject

class TvShowDetailActivity : BaseActivity<ActivityTvShowDetailBinding, TvShowDetailViewModel>(), OnLikeListener {

    @field: Inject
    lateinit var mViewModelFactory: ViewModelFactory

    private var mViewModel: TvShowDetailViewModel? = null

    private var tvShowId: Int = 0

    private lateinit var tvShow: Result

    private lateinit var likeButton: LikeButton

    private var isLiked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.getPresentationComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)
        tvShowId = intent.getIntExtra("TV_SHOW_ID", 0)
        setSupportActionBar(toolbar)
        init()
        registerHandlers()
        prepareLikeButton()
    }

    private fun prepareLikeButton() {
        mViewModel?.getSelectedTvShow(tvShowId, DatabaseOperationUseCases.Select {
            val tvShow: Result = it!![0]
            this.tvShow = tvShow
            if (tvShow.isFavourite == null) {
                likeButton.isLiked = false
            } else {
                likeButton.isLiked = tvShow.isFavourite
            }
        })
        likeButton.setOnLikeListener(this)
    }

    private fun init() {
        likeButton = findViewById(R.id.star_button)
    }

    private fun registerHandlers() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                    .setAction("", null).show()
        }
    }

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_tv_show_detail
    }

    override fun getViewModel(): TvShowDetailViewModel {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get<TvShowDetailViewModel>(TvShowDetailViewModel::class.java)
        return mViewModel as TvShowDetailViewModel
    }

    override fun liked(likeButton: LikeButton?) {
        isLiked = true
        mViewModel?.favouriteChange(tvShow, true)
    }

    override fun unLiked(likeButton: LikeButton?) {
        isLiked = false
        mViewModel?.favouriteChange(tvShow, false)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("IS_LIKED", isLiked)
        setResult(RESULT_OK, intent)
        finish()
    }
}
