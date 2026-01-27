package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class ContactActivity extends AppCompatActivity {

    private static final String UCK_PHONE = "tel:+48500100200";
    private static final String UCK_EMAIL = "mailto:kontakt@uck.gdansk.pl";
    private static final String UCK_MAP = "geo:0,0?q=UCK+Gdańsk";

    private static final String COORD_PHONE = "tel:+48500100300";
    private static final String COORD_EMAIL = "mailto:koordynator@uck.gdansk.pl";

    private static final String PSYCH_PHONE = "tel:+48500100400";
    private static final String PSYCH_EMAIL = "mailto:psycholog@uck.gdansk.pl";

    private static final String URGENT_PHONE = "tel:+48500100999";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_contact);

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
                startActivity(new Intent(this, VademecumActivity.class));
                finish();
                return true;

            } else if (id == R.id.nav_contact) {
                return true; // jesteś tu
            }

            return false;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kontakt");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // UCK
        MaterialButton btnUckCall = findViewById(R.id.btnUckCall);
        MaterialButton btnUckMail = findViewById(R.id.btnUckMail);
        MaterialButton btnUckMap  = findViewById(R.id.btnUckMap);

        btnUckCall.setOnClickListener(v -> openUri(UCK_PHONE));
        btnUckMail.setOnClickListener(v -> openUri(UCK_EMAIL));
        btnUckMap.setOnClickListener(v -> openUri(UCK_MAP));

        // Koordynator
        findViewById(R.id.btnCoordCall).setOnClickListener(v -> openUri(COORD_PHONE));
        findViewById(R.id.btnCoordMail).setOnClickListener(v -> openUri(COORD_EMAIL));

        // Psycholog
        findViewById(R.id.btnPsychCall).setOnClickListener(v -> openUri(PSYCH_PHONE));
        findViewById(R.id.btnPsychMail).setOnClickListener(v -> openUri(PSYCH_EMAIL));

        // Pilne
        findViewById(R.id.btnUrgentCall).setOnClickListener(v -> openUri(URGENT_PHONE));
    }

    private void openUri(String uri) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(i);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
