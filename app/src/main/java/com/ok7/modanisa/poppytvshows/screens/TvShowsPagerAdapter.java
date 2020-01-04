package com.ok7.modanisa.poppytvshows.screens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.ok7.modanisa.poppytvshows.common.BindingUtils;
import com.ok7.modanisa.poppytvshows.databinding.PopularTvShowPagerItemBinding;
import com.ok7.modanisa.poppytvshows.model.Result;

import java.util.List;

public class TvShowsPagerAdapter extends PagerAdapter implements BindingUtils.BindablePagerAdapter<Result> {

    private List<Result> listOfData;

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        final Result result = listOfData.get(position);
        PopularTvShowPagerItemBinding itemBinding = PopularTvShowPagerItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        itemBinding.setItem(result);
        parent.addView(itemBinding.getRoot());
        itemBinding.getRoot().setOnLongClickListener(v -> {
            Toast.makeText(parent.getContext(), result.getName(), Toast.LENGTH_LONG).show();
            return false;
        });
        return itemBinding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return listOfData != null ? listOfData.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setData(List<Result> listOfData) {
        if (this.listOfData != listOfData) {
            this.listOfData = listOfData;
            notifyDataSetChanged();
        }
    }
}