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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MedicationActivity extends AppCompatActivity {

    private EditText etSearch;
    private ListView listMeds;
    private ArrayAdapter<String> adapter;

    // Model danych leku
    public static class Medication implements Serializable {
        String name;
        String ingredients;
        String dosage;
        String notes;

        Medication(String name, String ingredients, String dosage, String notes) {
            this.name = name;
            this.ingredients = ingredients;
            this.dosage = dosage;
            this.notes = notes;
        }

        boolean matches(String query) {
            String q = query.toLowerCase(Locale.ROOT);
            return name.toLowerCase(Locale.ROOT).contains(q) || 
                   ingredients.toLowerCase(Locale.ROOT).contains(q);
        }
    }

    private final List<Medication> allMedications = Arrays.asList(
            new Medication("Prograf", "Takrolimus jednowodny", "Zazwyczaj 2x dziennie (co 12h)", "Przyjmować na czczo lub 2-3h po posiłku. Unikać grejpfrutów."),
            new Medication("Advagraf", "Takrolimus jednowodny", "1x dziennie rano", "Kapsułka o przedłużonym uwalnianiu. Przyjmować na czczo."),
            new Medication("Envarsus", "Takrolimus jednowodny", "1x dziennie o stałej porze", "Nowoczesna formuła o wysokiej biodostępności."),
            new Medication("CellCept", "Mykofenolan mofetylu", "Zazwyczaj 2x 500mg - 1000mg", "Nie rozgniatać tabletek. Chronić przed światłem."),
            new Medication("Myfortic", "Kwas mykofenolowy", "Zazwyczaj 2x 360mg - 720mg", "Tabletki dojelitowe - łagodniejsze dla żołądka."),
            new Medication("Encorton", "Prednizon", "Dawka ustalona indywidualnie", "Przyjmować rano, najlepiej po śniadaniu."),
            new Medication("Certican", "Ewerolimus", "2x dziennie", "Wymaga regularnego monitorowania stężenia we krwi."),
            new Medication("Biseptol", "Sulfametoksazol + Trimetoprim", "Zazwyczaj 1x dziennie (pon, śr, pt)", "Profilaktyka zakażeń układu oddechowego."),
            new Medication("Vigantol", "Cholekalcyferol (Wit. D3)", "Raz dziennie (krople lub tabl)", "Wsparcie kości przy sterydoterapii.")
    );

    private final ArrayList<String> filteredNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);
        
        setupBottomNav();
        etSearch = findViewById(R.id.etSearch);
        listMeds = findViewById(R.id.listMeds);

        updateFilteredList("");

        adapter = new ArrayAdapter<>(this, R.layout.lekiitem, R.id.tvName, filteredNames);
        listMeds.setAdapter(adapter);

        listMeds.setOnItemClickListener((parent, view, position, id) -> {
            String selectedName = filteredNames.get(position);
            Medication selectedMed = null;
            for (Medication m : allMedications) {
                if (m.name.equals(selectedName)) {
                    selectedMed = m;
                    break;
                }
            }

            if (selectedMed != null) {
                Intent intent = new Intent(this, MedicationDetailsActivity.class);
                intent.putExtra("med_object", selectedMed);
                startActivity(intent);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateFilteredList(s.toString());
                adapter.notifyDataSetChanged();
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void updateFilteredList(String query) {
        String q = query.trim().toLowerCase(Locale.ROOT);
        filteredNames.clear();
        for (Medication m : allMedications) {
            if (q.isEmpty() || m.matches(q)) {
                filteredNames.add(m.name);
            }
        }
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.nav_leki);
            bottomNav.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_start) { startActivity(new Intent(this, PanelActivity.class)); finish(); return true; }
                if (id == R.id.nav_leki) return true;
                if (id == R.id.nav_measures) { startActivity(new Intent(this, MeasurmentsDashboardActivity.class)); finish(); return true; }
                if (id == R.id.nav_vademecum) { startActivity(new Intent(this, VademecumActivity.class)); finish(); return true; }
                if (id == R.id.nav_contact) { startActivity(new Intent(this, ContactActivity.class)); finish(); return true; }
                return false;
            });
        }
    }
}
