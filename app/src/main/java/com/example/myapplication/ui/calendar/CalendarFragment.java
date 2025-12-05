package com.example.myapplication.ui.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private GridLayout grid;
    private TextView tvMonth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        grid = root.findViewById(R.id.gridCalendar);
        tvMonth = root.findViewById(R.id.tvMonth);

        setupCalendar();

        return root;
    }

    private void setupCalendar() {

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // nazwy miesięcy PL
        String[] months = {
                "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
                "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"
        };

        tvMonth.setText(months[month] + " " + year);

        // ustaw pierwszy dzień miesiąca
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (firstDay < 0) firstDay = 6;

        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        grid.removeAllViews();

        // generujemy 42 komórki
        for (int i = 0; i < 42; i++) {

            TextView tv = new TextView(getContext());
            tv.setPadding(12, 12, 12, 12);
            tv.setBackgroundResource(R.drawable.day_cell);
            tv.setTextSize(16);
            tv.setTextColor(Color.WHITE);
            tv.setGravity(View.TEXT_ALIGNMENT_CENTER);

            int dayNum = i - firstDay + 1;

            if (dayNum > 0 && dayNum <= maxDays)
                tv.setText(String.valueOf(dayNum));
            else
                tv.setText("");

            grid.addView(tv);
        }
    }
}
