package com.ok7.modanisa.poppytvshows.screens;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ok7.modanisa.poppytvshows.common.BindingUtils;
import com.ok7.modanisa.poppytvshows.databinding.PagerPopularTvShowBinding;
import com.ok7.modanisa.poppytvshows.databinding.RvPopularTvShowGridBinding;
import com.ok7.modanisa.poppytvshows.model.PopularShowSections;
import com.ok7.modanisa.poppytvshows.model.PopularTvShowsViewTypes;
import com.ok7.modanisa.poppytvshows.model.Result;
import com.ok7.modanisa.poppytvshows.model.TvShows;

import java.util.List;

public final class PopularTvShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements BindingUtils.BindableAdapter<PopularShowSections> {

    private List<PopularShowSections> listOfData;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == PopularTvShowsViewTypes.PAGER.getValue()) {
            PagerPopularTvShowBinding itemBinding = PagerPopularTvShowBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new TvShowPagerVH(itemBinding);
        } else if (viewType == PopularTvShowsViewTypes.HORIZONTAL.getValue()) {
            return null;
        } else {
            RvPopularTvShowGridBinding itemBinding = RvPopularTvShowGridBinding.inflate(LayoutInflater.from(parent.getContext()),
                    parent, false);
            return new TvShowVH(itemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TvShowVH) {
            ((TvShowVH) holder).bind(listOfData.get(position).getTvShows());
        } else if (holder instanceof TvShowPagerVH) {
            ((TvShowPagerVH) holder).bind(listOfData.get(position).getTvShows());
        } else {

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

    public class TvShowVH extends RecyclerView.ViewHolder {

        private RvPopularTvShowGridBinding mBinding;

        private PopularTvShowsGridAdapter mGridAdapter;

        TvShowVH(@NonNull RvPopularTvShowGridBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            this.mGridAdapter = new PopularTvShowsGridAdapter();
            GridLayoutManager gridLayoutManager = new GridLayoutManager(binding.getRoot().getContext(), 2);
            mBinding.rvGridTvShows.setLayoutManager(gridLayoutManager);
            mBinding.rvGridTvShows.setAdapter(this.mGridAdapter);
        }

        void bind(List<Result> list) {
            mBinding.setListOfShows(list);
            mBinding.executePendingBindings();
        }
    }

    public class TvShowPagerVH extends RecyclerView.ViewHolder {

        private PagerPopularTvShowBinding mBinding;

        private TvShowsPagerAdapter mTvShowsPagerAdapter;

        TvShowPagerVH(@NonNull PagerPopularTvShowBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            this.mTvShowsPagerAdapter = new TvShowsPagerAdapter();
            mBinding.pagerTvShows.setAdapter(this.mTvShowsPagerAdapter);
        }

        void bind(List<Result> list) {
            mBinding.setListOfShows(list);
            mBinding.executePendingBindings();
        }
    }
}
