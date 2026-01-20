package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class CalendarActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private RecyclerView rvDayTasks;
    private TextView tvSelectedDate;
    private DayTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);

        // Strzałka wstecz w toolbarze (opcjonalnie)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kalendarz");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        calendarView = findViewById(R.id.materialCalendar);
        rvDayTasks = findViewById(R.id.rvDayTasks);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);

        adapter = new DayTaskAdapter(new DayTaskAdapter.Listener() {
            @Override
            public void onTaskChecked(DayTask task, boolean checked) {
                // TODO: zapis do bazy
            }

            @Override
            public void onTaskAction(DayTask task) {
                // TODO: np. MapActivity
            }
        });

        rvDayTasks.setLayoutManager(new LinearLayoutManager(this));
        rvDayTasks.setAdapter(adapter);

        // Dzisiejszy dzień
        CalendarDay today = CalendarDay.today();
        calendarView.setSelectedDate(today);
        updateSelectedDateText(today);
        adapter.setItems(fakeTasksForDay(today));

        // Kliknięcie dnia
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            updateSelectedDateText(date);
            adapter.setItems(fakeTasksForDay(date));
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void updateSelectedDateText(CalendarDay date) {
        String formatted = String.format(
                Locale.getDefault(),
                "%04d-%02d-%02d",
                date.getYear(),
                date.getMonth(),
                date.getDay()
        );
        tvSelectedDate.setText("Plan dnia – " + formatted);
    }

    // Tymczasowe dane – potem zastąpisz Room
    private List<DayTask> fakeTasksForDay(CalendarDay date) {
        List<DayTask> list = new ArrayList<>();

        list.add(new DayTask(DayTask.Type.MEDICATION, "Tacrolimus", "Dawka: 2 mg", "08:00", false));
        list.add(new DayTask(DayTask.Type.INHALATION, "Inhalacja", "Nebulizacja", "09:00", false));
        list.add(new DayTask(DayTask.Type.MEASUREMENT, "Saturacja", "Wpisz SpO₂", "12:00", false));

        if (date.getDay() % 5 == 0) {
            list.add(new DayTask(DayTask.Type.VISIT, "Wizyta kontrolna", "UCK – Poradnia", "14:30", false));
        }

        list.add(new DayTask(DayTask.Type.DIARY, "Dzienniczek objawów", "Szybki check-in", "19:00", false));

        return list;
    }
}