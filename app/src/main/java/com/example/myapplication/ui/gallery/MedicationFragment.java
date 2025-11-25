package com.example.myapplication.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MedicationFragment extends Fragment {

        private RecyclerView recyclerView;
        private FloatingActionButton fabAdd;
        private MedicationAdapter adapter;
        private ArrayList<Medication> medications;

        public MedicationFragment() {
            // wymagany pusty konstruktor
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_medications_list, container, false);

            recyclerView = root.findViewById(R.id.recyclerMedications);
            fabAdd = root.findViewById(R.id.fabAddMedication);

            medications = new ArrayList<>();
            medications.add(new Medication("Tacrolimus", "1 mg", "08:00"));
            medications.add(new Medication("Mykofenolan mofetylu", "500 mg", "08:00 / 20:00"));
            medications.add(new Medication("Prednizon", "10 mg", "09:00"));

            adapter = new MedicationAdapter(medications);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            fabAdd.setOnClickListener(v -> {
                medications.add(new Medication(
                        "Nowy lek " + (medications.size() + 1),
                        "0 mg",
                        "00:00"
                ));
                adapter.notifyItemInserted(medications.size() - 1);
            });

            return root;
        }
    }



