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
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.ViewContainer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class CalendarFragment extends Fragment {

    private LocalDate selectedDate = null;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        TextView tvSelectedDate = view.findViewById(R.id.tvSelectedDate);

        YearMonth currentMonth = YearMonth.now();
        YearMonth startMonth = currentMonth.minusMonths(12);
        YearMonth endMonth = currentMonth.plusMonths(12);

        calendarView.setup(startMonth, endMonth, DayOfWeek.MONDAY);
        calendarView.scrollToMonth(currentMonth);

        class DayContainer extends ViewContainer {
            TextView dayText;
            CalendarDay day;

            DayContainer(@NonNull View v) {
                super(v);
                dayText = v.findViewById(R.id.dayText);

                v.setOnClickListener(clicked -> {
                    if (day.getPosition() == DayPosition.MonthDate) {
                        selectedDate = day.getDate();
                        tvSelectedDate.setText("Wybrana data: " + selectedDate);
                        calendarView.notifyDateChanged(day.getDate());
                    }
                });
            }
        }

        calendarView.setDayBinder(new MonthDayBinder<DayContainer>() {
            @NonNull
            @Override
            public DayContainer create(@NonNull View v) {
                View dayView = getLayoutInflater().inflate(R.layout.item_calendar_day, null, false);
                return new DayContainer(dayView);
            }

            @Override
            public void bind(@NonNull DayContainer container, @NonNull CalendarDay day) {
                container.day = day;
                container.dayText.setText(String.valueOf(day.getDate().getDayOfMonth()));

                if (day.getPosition() != DayPosition.MonthDate) {
                    container.dayText.setAlpha(0.3f);
                } else {
                    container.dayText.setAlpha(1f);
                }

                // Prosty highlight klikniętej daty
                if (selectedDate != null && day.getDate().equals(selectedDate)) {
                    container.dayText.setScaleX(1.1f);
                    container.dayText.setScaleY(1.1f);
                } else {
                    container.dayText.setScaleX(1f);
                    container.dayText.setScaleY(1f);
                }
            }
        });

        calendarView.setMonthScrollListener((CalendarMonth month) -> {
            // jak chcesz, tu ustawisz nagłówek miesiąca
            return null;
        });
    }
}
