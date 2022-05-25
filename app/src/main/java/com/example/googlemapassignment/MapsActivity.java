package com.example.googlemapassignment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemapassignment.databinding.ActivityMapsBinding;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    /**
     * Google map
     */
    private GoogleMap mMap;
    /**
     * Bind layout data
     */
    private ActivityMapsBinding binding;

    /**
     * Define list to get all lat-long for the route
     */
    private List<LatLng> path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Kochi(start location) and move the camera
        LatLng kochi = new LatLng(9.939093, 76.270523);
        mMap.addMarker(new MarkerOptions().position(kochi).title(getString(R.string.str_city_kochi)));

        mMap = googleMap;
        //second location
        LatLng coimbatore = new LatLng(11.004556, 76.961632);
        mMap.addMarker(new MarkerOptions().position(coimbatore).title(getString(R.string.str_city_coimbatore)));

        //third location
        LatLng madurai = new LatLng(9.939093, 78.121719);
        mMap.addMarker(new MarkerOptions().position(madurai).title(getString(R.string.str_city_madurai)));

        //fourth location
        LatLng munnar = new LatLng(10.089167, 77.059723);
        mMap.addMarker(new MarkerOptions().position(munnar).title(getString(R.string.str_city_munnar)));

        //Back to start location
        mMap.addMarker(new MarkerOptions().position(kochi).title(getString(R.string.str_city_kochi)));
    }
}