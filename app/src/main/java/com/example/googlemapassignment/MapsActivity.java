package com.example.googlemapassignment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.googlemapassignment.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
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

        //initializing  array
        path = new ArrayList<>();

        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(getString(R.string.str_google_maps_key))//add your API key here
                .build();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, "9.939093,76.270523", "9.939093,76.270523");
        try {
            DirectionsResult res = req.await();

            //Loop through legs and steps to get encoded poly-lines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs != null) {
                    for (int i = 0; i < route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j = 0; j < leg.steps.length; j++) {
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length > 0) {
                                    for (int k = 0; k < step.steps.length; k++) {
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> listCoordinates = points1.decodePath();
                                            for (com.google.maps.model.LatLng coordinates : listCoordinates) {
                                                path.add(new LatLng(coordinates.lat, coordinates.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> listCoordinates = points.decodePath();
                                        for (com.google.maps.model.LatLng coordinates : listCoordinates) {
                                            path.add(new LatLng(coordinates.lat, coordinates.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //Draw the polyline
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.RED).width(5);
            mMap.addPolyline(opts);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(madurai));
    }
}