package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class PanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            setContentView(R.layout.menu_activity);
        } catch (Exception e) {
            Toast.makeText(this, "Błąd layoutu: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        // POBIERANIE LOGINU I WYŚWIETLENIE GO W POWITANIU
        String userLogin = getIntent().getStringExtra("USER_LOGIN");
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        if (tvWelcome != null && userLogin != null && !userLogin.isEmpty()) {
            tvWelcome.setText("Witaj, " + userLogin + "!");
        }

        setupTiles();
        setupBottomNav();
    }

    private void setupTiles() {
        MaterialCardView cardToday = findViewById(R.id.cardToday);
        MaterialCardView cardCalendar = findViewById(R.id.cardCalendar);
        MaterialCardView cardDzienniczek = findViewById(R.id.cardDzienniczek);
        MaterialCardView cardVideo = findViewById(R.id.cardVideo);
        MaterialCardView cardMapa = findViewById(R.id.cardMapa);

        if (cardToday != null) {
            cardToday.setOnClickListener(v -> safeStart(CalendarActivity.class));
        }
        if (cardCalendar != null) {
            cardCalendar.setOnClickListener(v -> safeStart(CalendarActivity.class));
        }
        if (cardDzienniczek != null) {
            cardDzienniczek.setOnClickListener(v -> safeStart(DiaryActivity.class));
        }
        if (cardVideo != null) {
            cardVideo.setOnClickListener(v -> safeStart(VideoActivity.class));
        }
        if (cardMapa != null) {
            cardMapa.setOnClickListener(v -> safeStart(MapsActivity.class));
        }
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        if (bottomNav != null) {
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_start) return true;
                if (id == R.id.nav_leki) safeStart(MedicationActivity.class);
                if (id == R.id.nav_measures) safeStart(MeasurmentsDashboardActivity.class);
                if (id == R.id.nav_vademecum) safeStart(VademecumActivity.class);
                if (id == R.id.nav_contact) safeStart(ContactActivity.class);
                return true;
            });
        }
    }

    private void safeStart(Class<?> activityClass) {
        try {
            startActivity(new Intent(this, activityClass));
        } catch (Exception e) {
            Toast.makeText(this, "Nie można otworzyć: " + activityClass.getSimpleName(), Toast.LENGTH_SHORT).show();
        }
    }
}
