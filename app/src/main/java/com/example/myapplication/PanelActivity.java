package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class PanelActivity extends AppCompatActivity {

    private MaterialCardView cardCalendar, cardDzienniczek, cardVideo, cardMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        MaterialCardView cardVideos = findViewById(R.id.cardVideo);

        cardVideos.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.cardVideo, new VideoActivity())
                    .addToBackStack(null)
                    .commit();
        });
        // KAFELKI, KTÓRE NAPRAWDĘ ISTNIEJĄ W menu_activity.xml
        cardCalendar    = findViewById(R.id.cardCalendar);
        cardDzienniczek = findViewById(R.id.cardDzienniczek);
        //cardVideo       = findViewById(R.id.cardVideo);
        cardMapa        = findViewById(R.id.cardMapa);

        cardCalendar.setOnClickListener(v ->
                startActivity(new Intent(this, CalendarActivity.class)));

        cardDzienniczek.setOnClickListener(v ->
                startActivity(new Intent(this, DairyFragment.class)));

       // cardVideo.setOnClickListener(v ->
               // startActivity(new Intent(this, Vid.class)));

        cardMapa.setOnClickListener(v ->
                startActivity(new Intent(this, MapsActivity.class)));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_start) {
                return true;
            } else if (id == R.id.nav_leki) {
                startActivity(new Intent(this, MedicationActivity.class));
                return true;
            } else if (id == R.id.nav_measures) {
                startActivity(new Intent(this, MeasurmentsDashboardActivity.class));
                return true;
            } else if (id == R.id.nav_vademecum) {
                startActivity(new Intent(this, VademecumActivity.class));
                return true;
            } else if (id == R.id.nav_contact) {
                startActivity(new Intent(this, ContactActivity.class));
                return true;
            }

            return false;
        });
    }
}