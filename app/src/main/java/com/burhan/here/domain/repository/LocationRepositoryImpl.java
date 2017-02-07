package com.burhan.here.domain.repository;

import com.burhan.here.domain.model.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BURHAN on 2/5/2017.
 */

public class LocationRepositoryImpl implements LocationRepository {
    private final List<Location> locations = new ArrayList<>();

    @Override
    public List<Location> getAllLocations() {
        return locations;
    }

    @Override
    public void insertLocation(Location location) {
        if (location != null) {
            locations.add(location);
        }
    }

    @Override
    public void insertLocations(List<Location> _locations) {
        if (_locations != null) {
            locations.addAll(_locations);
        }
    }

    @Override
    public void removeAllLocations() {
        locations.clear();
    }
}
