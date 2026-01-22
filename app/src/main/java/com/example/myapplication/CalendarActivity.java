package com.example.myapplication;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView tvMonthLabel, tvSelectedDate;
    private RecyclerView rvDayTasks;
    private DayTaskAdapter adapter;
    private final List<DayTask> tasks = new ArrayList<>();

    private final SimpleDateFormat monthFmt = new SimpleDateFormat("MMMM yyyy", new Locale("pl"));
    private final SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kalendarz");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        calendarView = findViewById(R.id.calendarView);
        tvMonthLabel = findViewById(R.id.tvMonthLabel);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        rvDayTasks = findViewById(R.id.rvDayTasks);

        MaterialButton btnToday = findViewById(R.id.btnToday);

        rvDayTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DayTaskAdapter(tasks, (pos, done) -> {
            // MVP: tylko zmiana w pamięci (potem zapis do bazy/Room)
        });
        rvDayTasks.setAdapter(adapter);

        // Ustaw start na dziś
        CalendarDay today = CalendarDay.today();
        calendarView.setSelectedDate(today);
        updateHeaderLabels(today.getDate());
        loadTasksForDate(today.getDate());

        btnToday.setOnClickListener(v -> {
            CalendarDay t = CalendarDay.today();
            calendarView.setCurrentDate(t);
            calendarView.setSelectedDate(t);
            updateHeaderLabels(t.getDate());
            loadTasksForDate(t.getDate());
        });

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            updateHeaderLabels(date.getDate());
            loadTasksForDate(date.getDate());
        });

        calendarView.setOnMonthChangedListener((widget, date) -> {
            tvMonthLabel.setText(monthFmt.format(date.getDate()));
        });

        // ustaw label miesiąca na starcie
        tvMonthLabel.setText(monthFmt.format(new Date()));
    }

    private void updateHeaderLabels(Date selectedDate) {
        tvSelectedDate.setText("Plan dnia: " + dateFmt.format(selectedDate));
    }

    // MVP: dane przykładowe. Potem pobierasz z bazy po dacie.
    private void loadTasksForDate(Date selectedDate) {
        tasks.clear();

        // Przykładowo: zawsze pokazuj 3 zadania
        tasks.add(new DayTask(DayTask.Type.MEDICATION,"08:00", "Tacrolimus", "1 mg • po posiłku",  true));
        tasks.add(new DayTask(DayTask.Type.INHALATION,"09:00", "Inhalacja", "Nebulizacja • 10 min",  false));
        tasks.add(new DayTask( DayTask.Type.MEASUREMENT,"12:00", "Pomiar saturacji", "Zapisz wartość w %", false));

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}