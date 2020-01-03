package com.ok7.modanisa.poppytvshows;

import android.util.Log;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BindingUtils {

    public interface BindableAdapter<T> {
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
            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    nextPage.onLoadMore();
                }
            });
            recyclerView.setItemViewCacheSize(30);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            ((BindableAdapter) adapter).setData(listOfData);
        } else {
            throw new IllegalStateException("Adapter doesn't implement BindableAdapter");
        }
    }
}
