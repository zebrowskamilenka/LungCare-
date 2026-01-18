package com.example.myapplication;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
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
            "Witamina D"
    );
    private final ArrayList<String> filteredMeds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications); // <- nazwa Twojego layoutu

        etSearch = findViewById(R.id.etSearch);

        listMeds = findViewById(R.id.listMeds);

        // start: pokazujemy wszystko
        filteredMeds.addAll(allMeds);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                filteredMeds
        );
        listMeds.setAdapter(adapter);

        // klik w lek (opcjonalnie)
        listMeds.setOnItemClickListener((parent, view, position, id) -> {
            String selected = filteredMeds.get(position);
            // tutaj możesz np. otworzyć szczegóły leku
        });

        // LIVE SEARCH po każdej literce
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
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