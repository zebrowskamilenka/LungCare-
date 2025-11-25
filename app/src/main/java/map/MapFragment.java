package map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UckMapFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            // 📍 UCK Gdańsk
            LatLng uck = new LatLng(54.3807, 18.6086);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uck, 16));

            // Pinezki:
            googleMap.addMarker(new MarkerOptions()
                    .position(uck)
                    .title("UCK - Uniwersyteckie Centrum Kliniczne"));

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(54.38057, 18.60785))
                    .title("Wejście główne"));

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(54.38106, 18.60990))
                    .title("Izba Przyjęć"));

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(54.38120, 18.60740))
                    .title("Klinika Transplantologii"));

            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(54.38095, 18.60826))
                    .title("Apteka Szpitalna"));
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_uck_map, container, false);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        return view;
    }
}
