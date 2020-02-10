package com.ok7.mvvm.poppytvshows.screens.popularshows;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.like.LikeButton;
import com.ok7.mvvm.poppytvshows.R;
import com.ok7.mvvm.poppytvshows.common.BindingUtils;
import com.ok7.mvvm.poppytvshows.databinding.PopularTvShowPagerItemBinding;
import com.ok7.mvvm.poppytvshows.model.Result;

import java.lang.reflect.Field;
import java.util.List;

public final class TvShowsPagerAdapter extends PagerAdapter implements BindingUtils.BindablePagerAdapter<Result> {

    private List<Result> listOfData;

    private PopularTvShowsAdapter.ClickListener clickListener;

    public TvShowsPagerAdapter(PopularTvShowsAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        final Result result = listOfData.get(position);
        PopularTvShowPagerItemBinding itemBinding = PopularTvShowPagerItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        itemBinding.setListener(clickListener);
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

    @Nullable
    public LikeButton getLikeButtonForCurrentView(ViewPager pagerTvShows) {
        LikeButton likeButton = null;
        final int childCount = pagerTvShows.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = pagerTvShows.getChildAt(i);
            final ViewPager.LayoutParams lp = (ViewPager.LayoutParams) child.getLayoutParams();
            int position = 0;
            try {
                Field f = lp.getClass().getDeclaredField("position");
                f.setAccessible(true);
                position = f.getInt(lp); //IllegalAccessException
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            if (position == pagerTvShows.getCurrentItem()) {
                likeButton = child.findViewById(R.id.star_button);
            }
        }
        return likeButton;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}