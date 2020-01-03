package com.ok7.modanisa.poppytvshows.model;

import java.util.List;

public final class PopularShowSections {

    private List<Result> tvShows;

    private String type;

    public PopularShowSections(List<Result> tvShows, String type) {
        this.tvShows = tvShows;
        this.type = type;
    }

    public List<Result> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<Result> tvShows) {
        this.tvShows = tvShows;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
