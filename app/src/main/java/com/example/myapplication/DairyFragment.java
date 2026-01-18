package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DairyFragment extends Fragment {

    private ArrayList<DairyEntry> entries = new ArrayList<>();
    private DairyAdapter adapter;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dairy, container, false);

        prefs = requireContext().getSharedPreferences("dairy", Context.MODE_PRIVATE);



        EditText etMood = root.findViewById(R.id.etMood);
        EditText etSymptoms = root.findViewById(R.id.etSymptoms);
        EditText etMeds = root.findViewById(R.id.etMeds);
        Button btnSave = root.findViewById(R.id.btnSave);
        TextView tvDate = root.findViewById(R.id.tvDate);





        String now = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                    .format(new Date());

        btnSave.setOnClickListener(v -> {
            String mood = etMood.getText().toString().trim();
            String symptoms = etSymptoms.getText().toString().trim();
            String meds = etMeds.getText().toString().trim();
            String date = tvDate.getText().toString().trim();

            // Prosta walidacja
            if (mood.isEmpty() && symptoms.isEmpty() && meds.isEmpty()) {
                Toast.makeText(requireContext(), "Uzupełnij przynajmniej jedno pole.", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("date", date);
            editor.putString("mood", mood);
            editor.putString("symptoms", symptoms);
            editor.putString("meds", meds);
            editor.apply();

            Toast.makeText(requireContext(), "Zapisano wpis ✅", Toast.LENGTH_SHORT).show();

            // Wyczyść pola
            etMood.setText("");
            etSymptoms.setText("");
            etMeds.setText("");
        });

        return root;
    }


    }

