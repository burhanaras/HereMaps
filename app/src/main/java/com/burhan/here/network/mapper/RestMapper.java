package com.burhan.here.network.mapper;

import com.burhan.here.R;
import com.burhan.here.domain.model.Location;
import com.burhan.here.network.model.PoiItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BURHAN on 2/5/2017.
 */


public class RestMapper {
    public List<Location> mapFromNetworkToDomainObject(PoiItem[] items) {
        List<Location> result = new ArrayList<>();
        if (items != null) {
            for (PoiItem poiItem : items) {
                if (poiItem != null) {
                    result.add(mapFromNetworkToDomainObject(poiItem));
                }
            }
        }
        return result;
    }

    private Location mapFromNetworkToDomainObject(PoiItem poiItem) {
        Location location = new Location();
        if (poiItem != null) {
            location.setName(poiItem.getTitle());
            location.setAddress(poiItem.getVicinity());
            location.setDistance(poiItem.getDistance());
            location.setIcon(R.drawable.pin);
            if (poiItem.getPosition() != null && poiItem.getPosition().length >= 2) {
                location.setLatitude(poiItem.getPosition()[0]);
                location.setLongitude(poiItem.getPosition()[1]);
            }
        }

        return location;
    }
}
