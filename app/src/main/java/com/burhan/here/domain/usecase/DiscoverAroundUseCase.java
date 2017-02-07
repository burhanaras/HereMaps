package com.burhan.here.domain.usecase;

import com.burhan.here.domain.interactor.DiscoverAroundInteractor;
import com.burhan.here.domain.model.Location;
import com.burhan.here.domain.repository.LocationRepository;

import java.util.List;

/**
 * Created by BURHAN on 2/5/2017.
 */

public class DiscoverAroundUseCase implements UseCase {

    private static final int MAX_VISIBLE_MARKER_COUNT = 5;
    private final LocationRepository locationRepository;
    private final List<Location> locations;
    private DiscoverAroundInteractor interactor;

    public DiscoverAroundUseCase(LocationRepository locationRepository, List<Location> locations, DiscoverAroundInteractor interactor) {

        this.locationRepository = locationRepository;
        this.locations = locations;
        this.interactor = interactor;
    }

    @Override
    public void execute() {
        // add to repository here
        List<Location> retrievedLocations = locationRepository.getAllLocations();
        if (interactor != null) {
            interactor.onLocationRemoved(retrievedLocations);
        }
        locationRepository.removeAllLocations();
        locationRepository.insertLocations(locations);

        int locationCount = retrievedLocations.size() > MAX_VISIBLE_MARKER_COUNT ? MAX_VISIBLE_MARKER_COUNT : retrievedLocations.size();
        retrievedLocations = retrievedLocations.subList(0, locationCount);

        //then notify interactor
        if (interactor != null) {
            interactor.onLocationDiscovered(retrievedLocations);
        }
    }

    public void setInteractor(DiscoverAroundInteractor interactor) {
        this.interactor = interactor;
    }
}
