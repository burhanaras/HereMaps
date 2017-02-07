package com.burhan.here.network.rest;

import android.util.Log;

import com.burhan.here.domain.interactor.DiscoverAroundInteractor;
import com.burhan.here.domain.model.Location;
import com.burhan.here.domain.repository.LocationRepository;
import com.burhan.here.domain.usecase.DiscoverAroundUseCase;
import com.burhan.here.network.mapper.RestMapper;
import com.burhan.here.network.model.SearchAroundResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BURHAN on 2/5/2017.
 */

public class RestServiceImpl implements RestService {
    private static String TAG = RestServiceImpl.class.getName();
    private LocationRepository locationRepository;
    private DiscoverAroundInteractor interactor;

    public RestServiceImpl() {

    }

    @Override
    public void findNearbyPlaces(double latitude, double longitude, String category) {
        Log.d(TAG, "findNearbyPlaces() called with: latitude = [" + latitude + "], longitude = [" + longitude + "], category = [" + category + "]");
        HereMapsApi webService = HereMapsApi.retrofit.create(HereMapsApi.class);
        String coordinates = new StringBuilder().append(latitude).append(",").append(longitude).toString();
        Call<SearchAroundResponse> call = webService.dicoverAround(coordinates, category, "NqQEkJpnU4b7nHuPQYR7", "hR5N_Eziri2D9j4Xdojjsg");
        Log.d(TAG, "findNearbyPlaces: " + call.request().toString());
        call.enqueue(new Callback<SearchAroundResponse>() {
            @Override
            public void onResponse(Call<SearchAroundResponse> call, Response<SearchAroundResponse> response) {
                List<Location> locations = new RestMapper().mapFromNetworkToDomainObject(response.body().getResults().getItems());
                new DiscoverAroundUseCase(locationRepository, locations, interactor).execute();
            }

            @Override
            public void onFailure(Call<SearchAroundResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void setInteractor(DiscoverAroundInteractor interactor) {
        this.interactor = interactor;
    }
}
