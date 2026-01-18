package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMapsBinding;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Przykład: ustaw pinezkę (wpisz tu współrzędne UCK, jeśli je masz)
        LatLng uck = new LatLng(54.36647, 18.62362); // przykładowo: Gdańsk (podmień)
        googleMap.addMarker(new MarkerOptions().position(uck).title("Uniwersyteckie Centrum Kliniczne"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uck, 14f));
    }
}



