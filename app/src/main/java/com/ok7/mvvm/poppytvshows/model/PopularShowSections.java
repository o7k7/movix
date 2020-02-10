package com.ok7.mvvm.poppytvshows.model;

import java.util.List;

public final class PopularShowSections {

    private List<Result> tvShows;

    private PopularTvShowsViewTypes type;

    public PopularShowSections(List<Result> tvShows, PopularTvShowsViewTypes type) {
        this.tvShows = tvShows;
        this.type = type;
    }

    public List<Result> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<Result> tvShows) {
        this.tvShows = tvShows;
    }

    public PopularTvShowsViewTypes getType() {
        return type;
    }

    public void setType(PopularTvShowsViewTypes type) {
        this.type = type;
    }
}
