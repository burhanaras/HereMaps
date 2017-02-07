package com.burhan.here.network.mapper;

import com.burhan.here.R;
import com.burhan.here.domain.model.Location;
import com.burhan.here.network.model.PoiItem;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by BURHAN on 2/7/2017.
 */
public class RestMapperTest {

    private RestMapper restMapper;

    @Before
    public void setUp() throws Exception {
        restMapper = new RestMapper();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void mapFromNetworkToDomainObject() throws Exception {

        String title="Dummy title";
        double distance=5.0;
        String icon="Dummy icon";
        double[] position={5,6};
        String vicinity="Dummy address";

        PoiItem[] pois = new PoiItem[1];
        pois[0] = new PoiItem();
        pois[0].setTitle(title);
        pois[0].setDistance(distance);
        pois[0].setIcon(icon);
        pois[0].setPosition(position);
        pois[0].setVicinity(vicinity);

        List<Location> locations = restMapper.mapFromNetworkToDomainObject(pois);

        assertEquals(pois.length, locations.size());
        assertEquals(pois[0].getTitle(), locations.get(0).getName());
        assertEquals(pois[0].getDistance(), locations.get(0).getDistance());
        assertEquals(R.drawable.pin, locations.get(0).getIcon());
        assertEquals(pois[0].getVicinity(), locations.get(0).getAddress());
        assertEquals(pois[0].getPosition()[0], locations.get(0).getLatitude());
        assertEquals(pois[0].getPosition()[1], locations.get(0).getLongitude());
    }

}