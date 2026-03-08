package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipGroup;

public class MapsActivity extends AppCompatActivity {

    private ImageView ivFloorPlan;
    private TextView tvCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ivFloorPlan = findViewById(R.id.ivFloorPlan);
        tvCurrentLocation = findViewById(R.id.tvCurrentLocation);
        ChipGroup chipGroupFloors = findViewById(R.id.chipGroupFloors);

        // Obsługa przełączania pięter
        if (chipGroupFloors != null) {
            chipGroupFloors.setOnCheckedStateChangeListener((group, checkedIds) -> {
                if (checkedIds.contains(R.id.chipFloor0)) {
                    updateFloor("Budynek CMI - Parter");
                } else if (checkedIds.contains(R.id.chipFloor1)) {
                    updateFloor("Budynek CMI - I Piętro");
                } else if (checkedIds.contains(R.id.chipFloor2)) {
                    updateFloor("Budynek CMI - II Piętro");
                }
            });
        }

        // Obsługa kliknięć w punkty (SALA/REJESTRACJA)
        if (findViewById(R.id.spotReception) != null) {
            findViewById(R.id.spotReception).setOnClickListener(v -> 
                Toast.makeText(this, "Rejestracja: Godziny 07:30 - 15:30", Toast.LENGTH_SHORT).show());
        }

        if (findViewById(R.id.spotElevator) != null) {
            findViewById(R.id.spotElevator).setOnClickListener(v -> 
                Toast.makeText(this, "Windy: Dojście do oddziałów płucnych", Toast.LENGTH_SHORT).show());
        }
    }

    private void updateFloor(String floorName) {
        tvCurrentLocation.setText(floorName);
        // Tu możesz dodać zmianę grafiki: ivFloorPlan.setImageResource(R.drawable.twoja_mapa_pietra);
        Toast.makeText(this, "Zmieniono poziom na: " + floorName, Toast.LENGTH_SHORT).show();
    }
}
