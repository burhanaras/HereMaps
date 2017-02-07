package com.burhan.here.network.rest;

/**
 * Created by BURHAN on 2/5/2017.
 */

public interface RestService {
    void findNearbyPlaces(double latitude, double longitude, String category);
}
