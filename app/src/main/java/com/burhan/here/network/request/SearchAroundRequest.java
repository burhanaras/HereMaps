package com.burhan.here.network.request;

/**
 * Created by BURHAN on 2/5/2017.
 */

public class SearchAroundRequest {
    public String category;
    public double latitude;
    public double longitude;

    public SearchAroundRequest() {
    }

    public SearchAroundRequest(String category, double latitude, double longitude) {
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
