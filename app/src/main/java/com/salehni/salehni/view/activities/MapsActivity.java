package com.salehni.salehni.view.activities;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.salehni.salehni.R;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.viewmodel.DirectionsJSONParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private MarkerOptions mMarkerOptions;
    private LatLng mOrigin;
    private LatLng mDestination;
    private Polyline mPolyline;

    boolean moveCamera = false;

    Marker markerDestination;
    Marker markerOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        getExtra();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    private void getExtra() {
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {
//            String lat = extras.getString(Constants.lat);
//            String lon = extras.getString(Constants.lon);

            String lat = "31.958662832";
            String lon = "35.903829718";
            mDestination = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getMyLocation();

        int height = 95;
        int width = 70;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.car_pic);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        markerDestination = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                .position(new LatLng(mDestination.latitude, mDestination.longitude))
                .title("abuhussein").snippet("android developer"));

        if (markerOrigin != null) {
            markerOrigin.remove();
        }

        BitmapDrawable bitmapdraw1 = (BitmapDrawable) getResources().getDrawable(R.drawable.mechanic_pic);
        Bitmap b1 = bitmapdraw1.getBitmap();
        Bitmap smallMarker1 = Bitmap.createScaledBitmap(b1, width, height, false);

        markerOrigin = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker1))
                .position(new LatLng(mOrigin.latitude, mOrigin.longitude))
                .title("ramamneh").snippet("android developer"));

        if (!moveCamera) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mOrigin, 12));
            moveCamera = true;
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (requestCode == 100) {
            if (!verifyAllPermissions(grantResults)) {
                Toast.makeText(getApplicationContext(), "No sufficient permissions", Toast.LENGTH_LONG).show();
            } else {
                getMyLocation();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean verifyAllPermissions(int[] grantResults) {

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void getMyLocation() {

        // Getting LocationManager object from System Service LOCATION_SERVICE
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mOrigin = new LatLng(location.getLatitude(), location.getLongitude());

                if (markerOrigin != null) {
                    markerOrigin.remove();
                }

                int height = 95;
                int width = 70;
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.mechanic_pic);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                markerOrigin = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        .position(new LatLng(mOrigin.latitude, mOrigin.longitude))
                        .title("ramamneh").snippet("android developer"));

                if (!moveCamera) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mOrigin, 12));
                    moveCamera = true;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location myLocation = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        mOrigin = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                100,
                0, mLocationListener);

        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
                mMap.setMyLocationEnabled(true);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, mLocationListener);

                mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        mDestination = latLng;
                        mMap.clear();
                        mMarkerOptions = new MarkerOptions().position(mDestination).title("Destination");
                        mMap.addMarker(mMarkerOptions);
                    }
                });

            } else {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                }, 100);
            }
        }
    }
}

