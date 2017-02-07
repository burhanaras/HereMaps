package com.burhan.here.domain.interactor;

import com.burhan.here.domain.model.Location;

import java.util.List;

/**
 * Created by BURHAN on 2/5/2017.
 */

public interface DiscoverAroundInteractor {
    void onLocationDiscovered(List<Location> locations);
    void onLocationRemoved(List<Location> locations);
}
