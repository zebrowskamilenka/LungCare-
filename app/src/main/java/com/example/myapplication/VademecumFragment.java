package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VademecumFragment extends AppCompatActivity {

    public VademecumFragment() {
        // wymagany pusty konstruktor
    }

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_vademecum, container, false);

//        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_nav);
//
//        // ✅ zaznacz aktualną zakładkę
//        bottomNav.setSelectedItemId(R.id.nav_vademecum);
//
//        bottomNav.setOnItemSelectedListener(item -> {
//            int id = item.getItemId();
//
//            if (id == R.id.nav_start) {
//                startActivity(new Intent(requireContext(), PanelActivity.class));
//                return true;
//
//            } else if (id == R.id.nav_leki) {
//                startActivity(new Intent(requireContext(), MedicationActivity.class));
//                return true;
//
//            } else if (id == R.id.nav_measures) {
//                startActivity(new Intent(requireContext(), MeasurmentsDashboardActivity.class));
//                return true;
//
//            } else if (id == R.id.nav_vademecum) {
//                // jesteś tu
//                return true;
//
//            } else if (id == R.id.nav_contact) {
//                startActivity(new Intent(requireContext(), ContactActivity.class));
//                return true;
//            }
//
//            return false;
//        });
//
//        return view;
//    }
}
