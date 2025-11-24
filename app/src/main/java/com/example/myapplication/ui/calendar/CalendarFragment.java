package com.example.myapplication.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    private TextView tvSelectedDate;

    public CalendarFragment() {
        // wymagany pusty konstruktor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        tvSelectedDate = root.findViewById(R.id.tvSelectedDate);
        calendarView = root.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // month jest od 0, więc +1
            String date = dayOfMonth + "." + (month + 1) + "." + year;
            tvSelectedDate.setText("Wybrana data: " + date);
            Toast.makeText(getContext(), "Wybrano: " + date, Toast.LENGTH_SHORT).show();
        });

        return root;
    }
}
