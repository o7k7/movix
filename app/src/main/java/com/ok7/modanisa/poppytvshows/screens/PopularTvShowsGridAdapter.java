package com.ok7.modanisa.poppytvshows.screens;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ok7.modanisa.poppytvshows.common.BindingUtils;
import com.ok7.modanisa.poppytvshows.databinding.PopularTvShowGridItemBinding;
import com.ok7.modanisa.poppytvshows.model.Result;

import java.util.List;

public final class PopularTvShowsGridAdapter extends RecyclerView.Adapter<PopularTvShowsGridAdapter.GridItemHolder>
        implements BindingUtils.BindableAdapter<Result> {

    private List<Result> listOfData;

    private PopularTvShowsAdapter.DetailedClickListener clickListener;

    private String adapterType;

    public PopularTvShowsGridAdapter(String adapterType, PopularTvShowsAdapter.DetailedClickListener clickListener) {
        this.clickListener = clickListener;
        this.adapterType = adapterType;
    }

    @NonNull
    @Override
    public PopularTvShowsGridAdapter.GridItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PopularTvShowGridItemBinding itemBinding = PopularTvShowGridItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        itemBinding.setListener(result -> clickListener.onClick(result, this.listOfData.indexOf(result), adapterType, this, null));
        return new GridItemHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularTvShowsGridAdapter.GridItemHolder holder, int position) {
        holder.bind(listOfData.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listOfData != null ? this.listOfData.size() : 0;
    }

    @Override
    public void setData(List<Result> listOfData) {
        if (this.listOfData != listOfData) {
            this.listOfData = listOfData;
            notifyDataSetChanged();
        }
    }

    class GridItemHolder extends RecyclerView.ViewHolder {
        private PopularTvShowGridItemBinding itemView;

        GridItemHolder(@NonNull PopularTvShowGridItemBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
        }

        void bind(Result result) {
            this.itemView.setItemGrid(result);
            this.itemView.executePendingBindings();
        }
    }
}
