package com.burhan.here.domain.repository;

import com.burhan.here.domain.model.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by BURHAN on 2/7/2017.
 */
public class LocationRepositoryImplTest {

    private LocationRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        repository = new LocationRepositoryImpl();
    }

    @After
    public void tearDown() throws Exception {
        repository = null;
    }

    @Test
    public void getAllLocationsShouldReturnEmptyArrayWhenFirstCreated() throws Exception {
        int expected = 0;
        int actual = repository.getAllLocations().size();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllLocations() throws Exception {
        //given
        List<Location> dummyData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dummyData.add(new Location());
        }
        //when
        repository.insertLocations(dummyData);

        //then
        int expected = dummyData.size();
        int actual = repository.getAllLocations().size();
        assertEquals(expected, actual);
    }

    @Test
    public void insertLocation() throws Exception {
        //given
        Location location = new Location();
        //when
        repository.insertLocation(location);
        //then
        int expected = 1;
        int actual = repository.getAllLocations().size();
        assertEquals(expected, actual);

        //when
        repository.insertLocation(location);
        //then
        expected = 2;
        actual = repository.getAllLocations().size();
        assertEquals(expected, actual);
    }

    @Test
    public void insertLocations() throws Exception {
        //given
        List<Location> dummyData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dummyData.add(new Location());
        }
        //when
        repository.insertLocations(dummyData);

        //then
        int expected = dummyData.size();
        int actual = repository.getAllLocations().size();
        assertEquals(expected, actual);
    }

    @Test
    public void removeAllLocations() throws Exception {
        //given
        List<Location> dummyData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dummyData.add(new Location());
        }
        //when
        repository.insertLocations(dummyData);

        //then
        int expected = dummyData.size();
        int actual = repository.getAllLocations().size();
        assertEquals(expected, actual);

        //when
        repository.removeAllLocations();

        //then
        expected = 0;
        actual = repository.getAllLocations().size();
        assertEquals(expected, actual);
    }

}