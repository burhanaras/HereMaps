package com.burhan.here.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by BURHAN on 2/5/2017.
 */

public class SearchResult {

    @SerializedName("items")
    private PoiItem[] items;

    public PoiItem[] getItems() {
        return items;
    }

    public void setItems(PoiItem[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "items=" + Arrays.toString(items) +
                '}';
    }
}
