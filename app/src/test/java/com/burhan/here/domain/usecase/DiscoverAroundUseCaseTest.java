package com.burhan.here.domain.usecase;

import com.burhan.here.domain.interactor.DiscoverAroundInteractor;
import com.burhan.here.domain.model.Location;
import com.burhan.here.domain.repository.LocationRepository;
import com.burhan.here.domain.repository.LocationRepositoryImpl;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BURHAN on 2/7/2017.
 */
public class DiscoverAroundUseCaseTest {

    private DiscoverAroundUseCase useCase;
    private LocationRepository repository;

    @Mock
    DiscoverAroundInteractor interactor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        repository = new LocationRepositoryImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldReturnMaximumFiveLocations() {

        final List<Location> locations = dummyLocationList(6);
        useCase = new DiscoverAroundUseCase(repository, locations, interactor);
        useCase.execute();
        Assert.assertEquals(locations.size(), repository.getAllLocations().size());

        locations.remove(5);
        Mockito.verify(interactor).onLocationDiscovered(locations);

    }

    @Test
    public void shouldReturnEmptyArrayWhenInitialized() {

        final List<Location> locations = new ArrayList<>();
        useCase = new DiscoverAroundUseCase(repository, locations, interactor);
        useCase.execute();
        Assert.assertEquals(locations.size(), repository.getAllLocations().size());
        Mockito.verify(interactor).onLocationDiscovered(locations);

    }

    @Test
    public void shouldReturnAllItemsWhenSizeIsSmallerThanFive() {

        final List<Location> locations = dummyLocationList(3);
        useCase = new DiscoverAroundUseCase(repository, locations, interactor);
        useCase.execute();
        Assert.assertEquals(locations.size(), repository.getAllLocations().size());
        Mockito.verify(interactor).onLocationDiscovered(locations);

    }

    private List<Location> dummyLocationList(int size) {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            locations.add(new Location());
        }
        return locations;
    }

}