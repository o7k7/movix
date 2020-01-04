package com.ok7.modanisa.poppytvshows.common.database;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DataTypeConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<Integer> fromString1(String value) {
        Type listType = new TypeToken<List<Integer>>() {
        }.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromIntList(List<Integer> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS'Z'", Locale.getDefault());

    static {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @TypeConverter
    public static Date timeToDate(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                Log.e("TEST::", e.getMessage());
            }
            return null;
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String dateToTime(Date value) {
        if (value != null) {
            return df.format(value);
        } else {
            return null;
        }
    }
}