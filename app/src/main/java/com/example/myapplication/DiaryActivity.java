package com.example.myapplication;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Locale;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "dairy_prefs";
    private static final String KEY_ENTRIES = "entries";

    private TextView tvDate;
    private EditText etMood, etSymptoms, etMeds;
    private Button btnSave;

    private SharedPreferences prefs;
    private Gson gson;
    private String selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dairy);
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        gson = new Gson();

        tvDate = findViewById(R.id.tvDate);
        etMood = findViewById(R.id.etMood);
        etSymptoms = findViewById(R.id.etSymptoms);
        etMeds = findViewById(R.id.etMeds);
        btnSave = findViewById(R.id.btnSave);
        selectedDate = new SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
        ).format(Calendar.getInstance().getTime());
        tvDate.setText(selectedDate);

        tvDate.setOnClickListener(v -> showDatePicker());
        btnSave.setOnClickListener(v -> saveEntry());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    Calendar chosen = Calendar.getInstance();
                    chosen.set(year, month, day);

                    selectedDate = new SimpleDateFormat(
                            "dd.MM.yyyy",
                            Locale.getDefault()
                    ).format(chosen.getTime());

                    tvDate.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

        private void saveEntry() {
            String mood = etMood.getText().toString().trim();
            String symptoms = etSymptoms.getText().toString().trim();
            String meds = etMeds.getText().toString().trim();

            if (mood.isEmpty() && symptoms.isEmpty() && meds.isEmpty()) {
                Toast.makeText(this, "Uzupełnij chociaż jedno pole", Toast.LENGTH_SHORT).show();
                return;
            }

            DairyEntry entry = new DairyEntry(selectedDate, mood, symptoms, meds);

            ArrayList<DairyEntry> entries = loadEntries();
            entries.add(0, entry); // najnowszy na górze

            prefs.edit()
                    .putString(KEY_ENTRIES, gson.toJson(entries))
                    .apply();

            Toast.makeText(this, "Zapisano wpis ✅", Toast.LENGTH_SHORT).show();

            // wyczyść pola
            etMood.setText("");
            etSymptoms.setText("");
            etMeds.setText("");
        }

        private ArrayList<DairyEntry> loadEntries() {
            String json = prefs.getString(KEY_ENTRIES, null);
            if (json == null) return new ArrayList<>();

            Type type = new TypeToken<ArrayList<DairyEntry>>() {
            }.getType();
            ArrayList<DairyEntry> list = gson.fromJson(json, type);
            return (list != null) ? list : new ArrayList<>();
        }
    }
