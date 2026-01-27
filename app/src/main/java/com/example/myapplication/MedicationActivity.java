package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MedicationActivity extends AppCompatActivity {

    private EditText etSearch;
    private ListView listMeds;

    private ArrayAdapter<String> adapter;

    private final List<String> allMeds = Arrays.asList(
            "Tacrolimus",
            "Mykofenolan mofetylu",
            "Prednizon",
            "Ibuprofen",
            "Paracetamol",
            "Aspiryna",
            "Witamina D",
            "Abacavir + Lamivudine Accord (Iviverz) (tabletki powlekane)",
            "Abacavir + Lamivudine Sandoz (tabletki powlekane)",
            "Abagat (kapsułki twarde)",
            "ABE (płyn na skórę)"
    );

    private final ArrayList<String> filteredMeds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        // bottom nav
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_leki);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_start) {
                startActivity(new Intent(this, PanelActivity.class));
                finish();
                return true;

            } else if (id == R.id.nav_leki) {
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
                startActivity(new Intent(this, ContactActivity.class));
                finish();
                return true;
            }

            return false;
        });

        etSearch = findViewById(R.id.etSearch);
        listMeds = findViewById(R.id.listMeds);

        // start: pokazujemy wszystko
        filteredMeds.addAll(allMeds);

        adapter = new ArrayAdapter<>(
                this,
                R.layout.lekiitem,
                R.id.tvName,
                filteredMeds
        );

        listMeds.setAdapter(adapter);

        // ✅ ustaw listener kliknięcia RAZ
        listMeds.setOnItemClickListener((parent, view, position, id) -> {
            String name = filteredMeds.get(position);

            Intent intent = new Intent(MedicationActivity.this, MedicationDetailsActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        });

        // LIVE SEARCH
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void filterList(String query) {
        String q = query.trim().toLowerCase(Locale.ROOT);

        filteredMeds.clear();

        if (q.isEmpty()) {
            filteredMeds.addAll(allMeds);
        } else {
            for (String med : allMeds) {
                if (med.toLowerCase(Locale.ROOT).contains(q)) {
                    filteredMeds.add(med);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}
