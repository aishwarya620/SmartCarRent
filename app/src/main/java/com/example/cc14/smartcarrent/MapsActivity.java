package com.example.cc14.smartcarrent;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    private boolean mPermissionDenied = false;
    private UiSettings mUiSettings;
    private boolean showLevelPicker = true;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    LocationManager locationManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }  //end onCreate

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);

        mUiSettings.setRotateGesturesEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(18.3181, 73.4856), 18));
        Intent intent=getIntent();
        String product=intent.getStringExtra("Product").toString();

        String Latitude=intent.getStringExtra("Latitude").toString();

        String Logitude=intent.getStringExtra("Logitude").toString();



        //String newValue = Double.toString(Math.floor(percentageValue));
/*

        Double lati= Double.valueOf(Double.toString(Math.floor(Double.parseDouble(Latitude))));

        Double longi= Double.valueOf(Double.toString(Math.floor(Double.parseDouble(Logitude))));
*/


        /*lati /= 100;
        longi/=100;*/

        Double lat= Double.valueOf((Latitude));
        Double lon= Double.valueOf((Logitude));


        Toast.makeText(this, product+" "+" "+lat+" "+lat , Toast.LENGTH_LONG).show();

        Toast.makeText(this, product+" "+" "+lat+" "+lon , Toast.LENGTH_LONG).show();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(product));

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
        mMap.isTrafficEnabled();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

}
