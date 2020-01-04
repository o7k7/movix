package com.ok7.modanisa.poppytvshows.common;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.like.LikeButton;
import com.ok7.modanisa.poppytvshows.BuildConfig;
import com.squareup.picasso.Picasso;

import java.util.List;

public abstract class BindingUtils {

    public interface BindableAdapter<T> {
        void setData(List<T> listOfData);
    }

    public interface BindablePagerAdapter<T> {
        void setData(List<T> listOfData);
    }

    public interface NextPage {
        void onLoadMore();
    }

    /**
     * It sets RecyclerView's data and its related content. It is called from layout as app:data
     *
     * @param recyclerView
     * @param listOfData
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"data", "hasItemDecoration", "nextPage"}, requireAll = false)
    public static <T> void setRecyclerViewData(final RecyclerView recyclerView,
                                               final List<T> listOfData,
                                               final boolean hasItemDecoration,
                                               final NextPage nextPage) {
        final RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BindableAdapter) {
            if (BuildConfig.DEBUG && listOfData != null) {
                Log.e("TEST::", adapter.toString() + " " + listOfData.size());
            }
            if (recyclerView.getItemDecorationCount() == 0 && hasItemDecoration) {
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
            }
            initRecyclerViewListener(recyclerView, nextPage);
            recyclerView.setItemViewCacheSize(30);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            ((BindableAdapter) adapter).setData(listOfData);
        } else {
            throw new IllegalStateException("Adapter doesn't implement BindableAdapter");
        }
    }

    @BindingAdapter(value = {"pagerData"}, requireAll = false)
    public static <T> void setPagerData(final ViewPager pager,
                                        final List<T> listOfData) {
        final PagerAdapter adapter = pager.getAdapter();
        if (adapter instanceof BindablePagerAdapter) {
            if (BuildConfig.DEBUG && listOfData != null) {
                Log.e("TEST::", adapter.toString() + " " + listOfData.size());
            }
            ((BindablePagerAdapter) adapter).setData(listOfData);
        } else {
            throw new IllegalStateException("Adapter doesn't implement BindablePagerAdapter");
        }
    }

    @BindingAdapter(value = {"setImage"}, requireAll = false)
    public static void setPagerData(final ImageView imageView,
                                    String url) {
        Picasso.with(imageView.getContext()).load("https://image.tmdb.org/t/p/w185_and_h278_bestv2/" + url).into(imageView);
    }

    @BindingAdapter(value = {"setLiked"}, requireAll = false)
    public static void setPagerData(final LikeButton likeButton,
                                    Boolean isLiked) {
        if (isLiked == null) {
            likeButton.setLiked(false);
        } else {
            likeButton.setLiked(isLiked);
        }
    }

    private static boolean isLoading;

    private static int totalItemCount, visibleTotalCount, lastVisibleItem;

    private static void initRecyclerViewListener(RecyclerView recyclerView, final NextPage nextPage) {


        if (recyclerView == null) return;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (layoutManager == null) return;

                totalItemCount = layoutManager.getItemCount();
                visibleTotalCount = layoutManager.getChildCount();


                if (layoutManager instanceof GridLayoutManager) {
                    lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof LinearLayoutManager) {
                    lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;

                    int spanCount = staggeredGridLayoutManager.getSpanCount();
                    int[] lastPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[spanCount]);
                    lastVisibleItem = Math.max(lastPositions[0], lastPositions[1]);
                }


                if (totalItemCount <= visibleTotalCount) {
                    return;
                }

                if (!isLoading && (lastVisibleItem + visibleTotalCount) >= totalItemCount) {
                    if (nextPage != null) {
                        nextPage.onLoadMore();
                        isLoading = true;
                        new Handler().postDelayed(() -> {
                            isLoading = false;
                        }, 1000);
                    }
                }
            }
        });
    }
}
