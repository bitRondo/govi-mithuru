package com.example.govimithuruapp.core;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationController {
    private static final int DEFAULT_UPDATE_INTERVAL = 5;
    private static final int FAST_UPDATE_INTERVAL = 1;
    public static final int PERMISSIONS_FINE_LOCATION = 99;

    // Location Request is a config file for settings related to FusedLocationProviderClient
    private LocationRequest locationRequest;

    // Google's API for location services
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A callback to be triggered when location update interval is met
    private LocationCallback locationCallback;

    // A boolean to check if location update has been requested
    private boolean updateRequested = false;

    // A boolean to check if location permissions have been given
    private boolean hasPermissions = false;

    // Location parameters
    private Location location;

    // Singleton Pattern
    private static LocationController instance = null;

    public static LocationController getInstance() {
        if (instance == null) instance = new LocationController();
        return instance;
    }

    private LocationController() {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000 * DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(1000 * FAST_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                updateLocation(locationResult.getLastLocation());
            }
        };
    }

    public boolean checkHasPermissions() {
        return hasPermissions;
    }

    public void setHasPermissions(boolean hasPermissions) {
        this.hasPermissions = hasPermissions;
    }

    private void updateLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    public void attachActivity(Activity activity) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // permissions are available
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) updateLocation(location);
                }
            });
            if (!updateRequested) {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                updateRequested = true;
            }
        }
    }
}
