package com.ok7.mvvm.poppytvshows.screens.popularshows;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.like.LikeButton;
import com.ok7.mvvm.poppytvshows.common.BindingUtils;
import com.ok7.mvvm.poppytvshows.databinding.PagerPopularTvShowBinding;
import com.ok7.mvvm.poppytvshows.databinding.RvPopularTvShowGridBinding;
import com.ok7.mvvm.poppytvshows.model.PopularShowSections;
import com.ok7.mvvm.poppytvshows.model.PopularTvShowsViewTypes;
import com.ok7.mvvm.poppytvshows.model.Result;

import java.util.List;

public final class PopularTvShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements BindingUtils.BindableAdapter<PopularShowSections> {

    @FunctionalInterface
    public interface ClickListener {
        void onClick(Result result);
    }

    @FunctionalInterface
    public interface DetailedClickListener {
        void onClick(Result result, int index, String adapterType, Object adapter, @Nullable LikeButton likeButton);
    }

    @FunctionalInterface
    public interface DetailedPagerClickListener {
        void onClick(Result result, int index, String adapterType, PagerAdapter adapter);
    }

    private List<PopularShowSections> listOfData;

    private static DetailedClickListener clickListener;

    public PopularTvShowsAdapter(DetailedClickListener clickListener) {
        PopularTvShowsAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == PopularTvShowsViewTypes.PAGER.getValue()) {
            final PagerPopularTvShowBinding itemBinding = PagerPopularTvShowBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new TvShowPagerVH(itemBinding);
        } else if (viewType == PopularTvShowsViewTypes.HORIZONTAL.getValue()) {
            final RvPopularTvShowGridBinding itemBinding = RvPopularTvShowGridBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new TvShowHorizontalVH(itemBinding);
        } else {
            final RvPopularTvShowGridBinding itemBinding = RvPopularTvShowGridBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new TvShowVH(itemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TvShowHorizontalVH) {
            ((TvShowHorizontalVH) holder).bind(listOfData.get(position).getTvShows());
        } else if (holder instanceof TvShowPagerVH) {
            ((TvShowPagerVH) holder).bind(listOfData.get(position).getTvShows());
        } else {
            ((TvShowVH) holder).bind(listOfData.get(position).getTvShows());
        }
    }

    @Override
    public int getItemCount() {
        return listOfData != null ? listOfData.size() : 0;
    }

    @Override
    public void setData(List<PopularShowSections> data) {
        this.listOfData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (listOfData.get(position).getType().equals(PopularTvShowsViewTypes.PAGER)) {
            return PopularTvShowsViewTypes.PAGER.getValue();
        } else if (listOfData.get(position).getType().equals(PopularTvShowsViewTypes.HORIZONTAL)) {
            return PopularTvShowsViewTypes.HORIZONTAL.getValue();
        } else {
            return PopularTvShowsViewTypes.GRID.getValue();
        }
    }

    static final class TvShowVH extends RecyclerView.ViewHolder {

        private final RvPopularTvShowGridBinding mBinding;

        TvShowVH(@NonNull RvPopularTvShowGridBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            final PopularTvShowsGridAdapter mGridAdapter = new PopularTvShowsGridAdapter("VERTICAL", (result, index, adapterType, adapter, likeButton) -> {
                clickListener.onClick(result, index, adapterType, adapter, null);
            });
            mBinding.rvGridTvShows.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
            mBinding.rvGridTvShows.setAdapter(mGridAdapter);
        }

        void bind(List<Result> list) {
            mBinding.setListOfShows(list);
            mBinding.executePendingBindings();
        }
    }

    static final class TvShowHorizontalVH extends RecyclerView.ViewHolder {

        private RvPopularTvShowGridBinding mBinding;

        TvShowHorizontalVH(@NonNull RvPopularTvShowGridBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            final PopularTvShowsGridAdapter mGridAdapter = new PopularTvShowsGridAdapter("HORIZONTAL", (result, index, adapterType, adapter, likeButton) -> {
                clickListener.onClick(result, index, adapterType, adapter, null);
            });

            mBinding.rvGridTvShows.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false));
            mBinding.rvGridTvShows.setAdapter(mGridAdapter);
        }

        void bind(List<Result> list) {
            mBinding.setListOfShows(list);
            mBinding.executePendingBindings();
        }
    }

    static final class TvShowPagerVH extends RecyclerView.ViewHolder {

        private PagerPopularTvShowBinding mBinding;

        TvShowPagerVH(@NonNull PagerPopularTvShowBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            final TvShowsPagerAdapter mTvShowsPagerAdapter = new TvShowsPagerAdapter(result -> {
                if (mBinding.pagerTvShows.getAdapter() != null) {
                    clickListener.onClick(result, 0, "PAGER", mBinding.pagerTvShows.getAdapter(),
                            ((TvShowsPagerAdapter) mBinding.pagerTvShows.getAdapter()).getLikeButtonForCurrentView(mBinding.pagerTvShows));
                }
            });

            mBinding.pagerTvShows.setOffscreenPageLimit(10);
            mBinding.pagerTvShows.setAdapter(mTvShowsPagerAdapter);
        }

        void bind(List<Result> list) {
            mBinding.setListOfShows(list);
            mBinding.executePendingBindings();
        }
    }
}
