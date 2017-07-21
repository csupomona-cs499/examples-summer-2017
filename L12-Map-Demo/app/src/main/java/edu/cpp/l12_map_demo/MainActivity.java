package edu.cpp.l12_map_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                // Add a marker in Sydney, Australia,
                // and move the map's camera to the same location.

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        LatLng sydney = new LatLng(new Random().nextInt(180), new Random().nextInt(180));
                        googleMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_dialog_info))
                                .anchor(0.0f, 1.0f)
                                .position(sydney)
                                .title("Marker in a random location"));

                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                        return false;
                    }
                });

                LatLng sydney = new LatLng(-33.852, 151.211);
                googleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.ic_dialog_info))
                        .anchor(0.0f, 1.0f)
                        .position(sydney)
                        .title("Marker in Sydney"));

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });


    }
}
