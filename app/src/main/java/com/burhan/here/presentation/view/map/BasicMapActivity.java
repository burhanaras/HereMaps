/*
 * Copyright © 2011-2016 HERE Global B.V. and its affiliate(s).
 * All rights reserved.
 * The use of this software is conditional upon having a separate agreement
 * with a HERE company for the use or utilization of this software. In the
 * absence of such agreement, the use of the software is not allowed.
 */
package com.burhan.here.presentation.view.map;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.burhan.here.R;
import com.burhan.here.domain.repository.LocationRepository;
import com.burhan.here.domain.repository.LocationRepositoryImpl;
import com.burhan.here.network.request.SearchAroundRequest;
import com.burhan.here.network.rest.RestService;
import com.burhan.here.network.rest.RestServiceImpl;
import com.burhan.here.presentation.presenter.BasicMapPresenter;
import com.burhan.here.presentation.presenter.BasicMapPresenterImpl;
import com.burhan.here.presentation.util.MarkerImageHelper;
import com.burhan.here.presentation.view.map.listener.HereMapsGestureListener;
import com.burhan.here.presentation.view.nearby.NearbyDialog;
import com.burhan.here.presentation.view.nearby.listener.NearbyLocationsSelectionListener;
import com.burhan.here.presentation.view.permission.RuntimePermissionsActivity;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicMapActivity extends RuntimePermissionsActivity implements BasicMapPresenter.View, NearbyLocationsSelectionListener {
    public static final String TAG = BasicMapActivity.class.getSimpleName();

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final int REQUEST_CODE_FOR_OPEN_GPS_WINDOW = 2;

    public static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Map map = null;
    private MapFragment mapFragment = null;

    private LinearLayout root;
    private Button btnNearbyPlaces;
    private ImageButton btnLocateMe;
    private Location gpsLocation;
    private GeoCoordinate london = new GeoCoordinate(51.196261, 0.004773);
    private boolean centerMapToGpsLocation = false;
    private String currentlSelectedCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        Log.d(TAG, "initialize() called");
        setContentView(R.layout.activity_main);

        root = (LinearLayout) findViewById(R.id.root);
        // Search for the map fragment to finish setup by calling init().
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    Log.d(TAG, "onEngineInitializationCompleted: ");
                    mapFragment.getMapGesture().addOnGestureListener(new HereMapsGestureListener(BasicMapActivity.this, root));
                    map = mapFragment.getMap();
                    if (gpsLocation != null) {
                        map.setCenter(new GeoCoordinate(gpsLocation.getLatitude(), gpsLocation.getLongitude(), 0.0),
                                Map.Animation.LINEAR);
                    } else {
                        map.setCenter(london, Map.Animation.LINEAR);
                    }

                    map.setZoomLevel(5);
                } else {
                    Log.e(TAG, "Cannot initialize MapFragment (" + error + ")");
                }
            }
        });


        btnNearbyPlaces = (Button) findViewById(R.id.btn_nearby_places);
        btnNearbyPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NearbyDialog dialog = new NearbyDialog(BasicMapActivity.this, R.style.NearbyDialog);
                dialog.show();

            }
        });

        btnLocateMe = (ImageButton) findViewById(R.id.ibtn_locate_me);
        btnLocateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                centerMapToGpsLocation = true;
                checkGPSPermissions();
            }
        });
    }

    /**
     * Checks the dynamically controlled permissions and requests missing permissions from end user.
     */
    protected void checkGPSPermissions() {
        BasicMapActivity.super.requestAppPermissions(
                REQUIRED_SDK_PERMISSIONS, R.string.runtime_permissions_txt, REQUEST_CODE_ASK_PERMISSIONS);
    }


    @Override
    public void onPermissionsGranted(int requestCode) {
        Log.d(TAG, "onPermissionsGranted() called with: requestCode = [" + requestCode + "]");
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, BasicMapActivity.this);
                gpsLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if (gpsLocation != null) {
                    map.setCenter(new GeoCoordinate(gpsLocation.getLatitude(), gpsLocation.getLongitude()), Map.Animation.LINEAR);
                }

            }
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.open_gps_message))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, REQUEST_CODE_FOR_OPEN_GPS_WINDOW);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (requestCode == REQUEST_CODE_FOR_OPEN_GPS_WINDOW) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, BasicMapActivity.this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, BasicMapActivity.this);
                gpsLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if (gpsLocation != null) {
                    map.setCenter(new GeoCoordinate(gpsLocation.getLatitude(), gpsLocation.getLongitude()), Map.Animation.LINEAR);
                }
            } else {
                Log.d(TAG, "onActivityResult: GPS not enabled.");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private LocationRepository repository = new LocationRepositoryImpl();
    private RestService restService = new RestServiceImpl();
    private SearchAroundRequest searchAroundRequest;

    @Override
    public void onNearbyCategorySelected(String category) {
        currentlSelectedCategory = category;
        Log.d(TAG, "onNearbyCategorySelected() called with: category = [" + category + "]");
        map.getCenter();
        searchAroundRequest = new SearchAroundRequest(category, map.getCenter().getLatitude(), map.getCenter().getLongitude());
        new BasicMapPresenterImpl(BasicMapActivity.this, restService, repository, searchAroundRequest).searchAroundLocations();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged() called with: gpsLocation = [" + location + "]");
        this.gpsLocation = location;
        if (centerMapToGpsLocation) {
            map.setCenter(new GeoCoordinate(location.getLatitude(), location.getLongitude()), Map.Animation.LINEAR);
            centerMapToGpsLocation = false;
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d(TAG, "onStatusChanged() called with: s = [" + s + "], i = [" + i + "], bundle = [" + bundle + "]");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d(TAG, "onProviderEnabled() called with: s = [" + s + "]");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d(TAG, "onProviderDisabled() called with: s = [" + s + "]");
    }

    private List<MapObject> markers = new ArrayList<>();

    @Override
    public void onAroundLocationsLoaded(List<com.burhan.here.domain.model.Location> locations) {
        Log.d(TAG, "onAroundLocationsLoaded() called with: locations = [" + locations + "]");

        for (com.burhan.here.domain.model.Location location : locations) {
            MapObject marker = new MapMarker(new GeoCoordinate(location.getLatitude(), location.getLongitude()), MarkerImageHelper.getMarkerImage(currentlSelectedCategory));
            ((MapMarker)marker).setDescription(location.getName());
            map.addMapObject(marker);
            markers.add(marker);
        }
        if (locations.size() > 0) {
            map.setZoomLevel(map.getMaxZoomLevel() - 5);
            map.setCenter(new GeoCoordinate(locations.get(0).getLatitude(), locations.get(0).getLongitude()), Map.Animation.LINEAR);
        }
    }

    @Override
    public void onLocationRemoved(List<com.burhan.here.domain.model.Location> locations) {
        map.removeMapObjects(markers);
    }
}