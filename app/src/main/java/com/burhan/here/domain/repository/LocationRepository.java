package com.burhan.here.domain.repository;

import com.burhan.here.domain.model.Location;

import java.util.List;

/**
 * Created by BURHAN on 2/5/2017.
 */

public interface LocationRepository {
    List<Location> getAllLocations();
    void insertLocation(Location location);
    void insertLocations(List<Location> locations);
    void removeAllLocations();
}
