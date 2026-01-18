package com.example.myapplication.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MedicationFragment extends Fragment {

    private ArrayList<String> medications;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_medications, container, false);

        EditText etSearch = view.findViewById(R.id.etSearch);
        ListView listMeds = view.findViewById(R.id.listMeds);

        medications = new ArrayList<>();
        medications.add("Tacrolimus – 5 mg – 08:00");
        medications.add("Mykofenolan mofetylu – 500 mg – 20:00");
        medications.add("Prednizon – 10 mg – 08:00");

        adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                medications
        );

        listMeds.setAdapter(adapter);

        // (opcjonalnie później: filtrowanie po etSearch)

        return view;
    }
}
