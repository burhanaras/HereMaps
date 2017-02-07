package com.burhan.here.gps;

import android.location.Location;

/**
 * Created by BURHAN on 2/4/2017.
 */

public interface LocationCallBack {
    void onLocationChanged(Location location);
    void onLocationDisabled();
}
