package com.burhan.here.presentation.presenter;

import com.burhan.here.domain.interactor.DiscoverAroundInteractor;
import com.burhan.here.domain.model.Location;
import com.burhan.here.network.request.SearchAroundRequest;
import com.burhan.here.network.rest.RestService;
import com.burhan.here.network.rest.RestServiceImpl;
import com.burhan.here.domain.repository.LocationRepository;

import java.util.List;

/**
 * Created by BURHAN on 2/5/2017.
 */

public class BasicMapPresenterImpl implements BasicMapPresenter {


    private BasicMapPresenter.View presenter;
    private RestServiceImpl restService;
    private LocationRepository repository;
    private SearchAroundRequest searchAroundRequest;

    private DiscoverAroundInteractor interactor = new DiscoverAroundInteractor() {
        @Override
        public void onLocationDiscovered(List<Location> locations) {
            if (locations != null) {
                presenter.onAroundLocationsLoaded(locations);
            }
        }

        @Override
        public void onLocationRemoved(List<Location> locations) {
            if (locations != null) {
                presenter.onLocationRemoved(locations);
            }
        }
    };

    public BasicMapPresenterImpl(BasicMapPresenter.View presenter, RestService _restService, LocationRepository _repository, SearchAroundRequest _searchAroundRequest) {
        this.presenter = presenter;
        this.restService = (RestServiceImpl) _restService;
        this.repository = _repository;
        this.searchAroundRequest = _searchAroundRequest;
        restService.setLocationRepository(repository);
        restService.setInteractor(interactor);

    }

    @Override
    public void searchAroundLocations() {
        restService.findNearbyPlaces(searchAroundRequest.latitude, searchAroundRequest.longitude, searchAroundRequest.category);

    }

    public void setRestService(RestService restService) {
        this.restService = (RestServiceImpl) restService;
    }
}
