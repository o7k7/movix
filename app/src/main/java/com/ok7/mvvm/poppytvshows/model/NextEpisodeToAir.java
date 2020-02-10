
package com.ok7.mvvm.poppytvshows.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class NextEpisodeToAir implements Parcelable {

    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episode_number")
    @Expose
    private Integer episodeNumber;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("production_code")
    @Expose
    private String productionCode;
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;
    @SerializedName("show_id")
    @Expose
    private Integer showId;
    @SerializedName("still_path")
    @Expose
    private String stillPath;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;


    public NextEpisodeToAir() {
    }

    protected NextEpisodeToAir(Parcel in) {
        airDate = in.readString();
        if (in.readByte() == 0) {
            episodeNumber = null;
        } else {
            episodeNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        overview = in.readString();
        productionCode = in.readString();
        if (in.readByte() == 0) {
            seasonNumber = null;
        } else {
            seasonNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            showId = null;
        } else {
            showId = in.readInt();
        }
        stillPath = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
        if (in.readByte() == 0) {
            voteCount = null;
        } else {
            voteCount = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(airDate);
        if (episodeNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(episodeNumber);
        }
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(overview);
        dest.writeString(productionCode);
        if (seasonNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(seasonNumber);
        }
        if (showId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(showId);
        }
        dest.writeString(stillPath);
        if (voteAverage == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(voteAverage);
        }
        if (voteCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(voteCount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NextEpisodeToAir> CREATOR = new Creator<NextEpisodeToAir>() {
        @Override
        public NextEpisodeToAir createFromParcel(Parcel in) {
            return new NextEpisodeToAir(in);
        }

        @Override
        public NextEpisodeToAir[] newArray(int size) {
            return new NextEpisodeToAir[size];
        }
    };

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
