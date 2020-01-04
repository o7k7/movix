package com.ok7.modanisa.poppytvshows.common.database;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public abstract class BaseModel {

    @PrimaryKey(autoGenerate = true)
    @Expose
    Long id = 0L;

    @ColumnInfo(name = "description")
    @SerializedName(value = "description")
    String description;

    @ColumnInfo(name = "creation_date")
    @SerializedName(value = "creation_date")
    Date creationDate = new Date(System.currentTimeMillis());

    @ColumnInfo(name = "modification_date")
    @SerializedName(value = "modification_date")
    Date modificationDate = new Date(System.currentTimeMillis());
}
