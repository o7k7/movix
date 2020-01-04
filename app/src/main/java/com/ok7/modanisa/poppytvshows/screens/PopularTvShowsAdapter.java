package com.ok7.modanisa.poppytvshows.screens;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ok7.modanisa.poppytvshows.common.BindingUtils;
import com.ok7.modanisa.poppytvshows.databinding.PagerPopularTvShowBinding;
import com.ok7.modanisa.poppytvshows.databinding.RvPopularTvShowGridBinding;
import com.ok7.modanisa.poppytvshows.model.PopularShowSections;
import com.ok7.modanisa.poppytvshows.model.PopularTvShowsViewTypes;
import com.ok7.modanisa.poppytvshows.model.Result;

import java.util.List;

public final class PopularTvShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements BindingUtils.BindableAdapter<PopularShowSections> {

    @FunctionalInterface
    public interface ClickListener {
        void onClick(Result result);
    }

    private List<PopularShowSections> listOfData;

    private static ClickListener clickListener;

    public PopularTvShowsAdapter(ClickListener clickListener) {
        PopularTvShowsAdapter.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == PopularTvShowsViewTypes.PAGER.getValue()) {
            final PagerPopularTvShowBinding itemBinding = PagerPopularTvShowBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            final TvShowPagerVH tvShowPagerVH = new TvShowPagerVH(itemBinding);
            return tvShowPagerVH;
        } else if (viewType == PopularTvShowsViewTypes.HORIZONTAL.getValue()) {
            final RvPopularTvShowGridBinding itemBinding = RvPopularTvShowGridBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            final TvShowHorizontalVH tvShowHorizontalVH = new TvShowHorizontalVH(itemBinding);
            tvShowHorizontalVH.itemView.setOnClickListener(v -> {
                final int pos = tvShowHorizontalVH.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {

                }
            });
            return tvShowHorizontalVH;
        } else {
            final RvPopularTvShowGridBinding itemBinding = RvPopularTvShowGridBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            final TvShowVH tvShowVH = new TvShowVH(itemBinding);
            tvShowVH.itemView.setOnClickListener(v -> {
                final int pos = tvShowVH.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {

                }
            });
            return tvShowVH;
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

        private RvPopularTvShowGridBinding mBinding;

        TvShowVH(@NonNull RvPopularTvShowGridBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            PopularTvShowsGridAdapter mGridAdapter = new PopularTvShowsGridAdapter(result -> {
                clickListener.onClick(result);
            });
            GridLayoutManager gridLayoutManager = new GridLayoutManager(binding.getRoot().getContext(), 2);
            mBinding.rvGridTvShows.setLayoutManager(gridLayoutManager);
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
            PopularTvShowsGridAdapter mGridAdapter = new PopularTvShowsGridAdapter(result -> {
                clickListener.onClick(result);
            });
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false);
            mBinding.rvGridTvShows.setLayoutManager(linearLayoutManager);
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
            TvShowsPagerAdapter mTvShowsPagerAdapter = new TvShowsPagerAdapter(result -> {
                clickListener.onClick(result);
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
