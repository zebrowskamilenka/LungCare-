package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

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



        RecyclerView recycler = root.findViewById(R.id.recyclerDiary);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DairyAdapter(entries);
        recycler.setAdapter(adapter);

        EditText et = root.findViewById(R.id.etDiaryInput);




            String date = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                    .format(new Date());




            et.setText("");
        ;

        return root;
    }


    }

