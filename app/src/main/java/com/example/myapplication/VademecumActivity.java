package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VademecumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_vademecum); // <-- dopasuj nazwę layoutu

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_vademecum);

            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();

                if (id == R.id.nav_start) {
                    startActivity(new Intent(this, PanelActivity.class));
                    finish();
                    return true;

                } else if (id == R.id.nav_leki) {
                    startActivity(new Intent(this, MedicationActivity.class));
                    finish();
                    return true;

                } else if (id == R.id.nav_measures) {
                    startActivity(new Intent(this, MeasurmentsDashboardActivity.class));
                    finish();
                    return true;

                } else if (id == R.id.nav_vademecum) {
                    return true; // jesteś tu

                } else if (id == R.id.nav_contact) {
                    startActivity(new Intent(this, ContactActivity.class));
                    finish();
                    return true;
                }

                return false;
            });
        }
    }
}
