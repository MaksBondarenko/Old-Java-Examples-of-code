package com.example.prm_02;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Photo {
    private double latitude;
    private double longitude;
    private String desc;
    private Bitmap bitmap;

    public Photo(double latitude, double longitude, String desc, Bitmap bitmap) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.desc = desc;
        this.bitmap = bitmap;
    }

    public Photo() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
