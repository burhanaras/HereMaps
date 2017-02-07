package com.burhan.here.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by BURHAN on 2/5/2017.
 */

public class PoiItem {
    @SerializedName("title")
    private String title;
    @SerializedName("position")
    private double[] position;
    @SerializedName("distance")
    private double distance;
    @SerializedName("icon")
    private String icon;
    @SerializedName("vicinity")
    private String vicinity;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    @Override
    public String toString() {
        return "PoiItem{" +
                "title='" + title + '\'' +
                ", position=" + Arrays.toString(position) +
                ", distance=" + distance +
                ", icon='" + icon + '\'' +
                ", vicinity='" + vicinity + '\'' +
                '}';
    }
}
