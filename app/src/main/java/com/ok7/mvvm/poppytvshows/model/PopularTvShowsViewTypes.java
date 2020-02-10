package com.ok7.mvvm.poppytvshows.model;

public enum PopularTvShowsViewTypes {
    PAGER(0), HORIZONTAL(1), GRID(2);


    final int value;

    PopularTvShowsViewTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
