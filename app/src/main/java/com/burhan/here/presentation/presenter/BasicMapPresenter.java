package com.burhan.here.presentation.presenter;

import com.burhan.here.domain.model.Location;

import java.util.List;

/**
 * Created by BURHAN on 2/5/2017.
 */

public interface BasicMapPresenter {
    public interface View{
        void onAroundLocationsLoaded(List<Location> locations);
        void onLocationRemoved(List<Location> locations);
    }

    void searchAroundLocations();
}
