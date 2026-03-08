package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

    private final SimpleDateFormat monthFmt = new SimpleDateFormat("MMMM yyyy", new Locale("pl", "PL"));
    private final SimpleDateFormat dateFmt = new SimpleDateFormat("d MMMM yyyy", new Locale("pl", "PL"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide(); // Ukrywamy stary pasek, mamy własny nagłówek
        }

        bindViews();
        setupRecyclerView();
        setupCalendar();
        setupButtons();
    }

    private void bindViews() {
        calendarView = findViewById(R.id.calendarView);
        tvMonthLabel = findViewById(R.id.tvMonthLabel);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        rvDayTasks = findViewById(R.id.rvDayTasks);
    }

    private void setupRecyclerView() {
        rvDayTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DayTaskAdapter(tasks, (pos, done) -> {
            // Logika zmiany statusu (opcjonalnie)
        });
        rvDayTasks.setAdapter(adapter);
    }

    private void setupCalendar() {
        CalendarDay today = CalendarDay.today();
        calendarView.setSelectedDate(today);
        calendarView.setCurrentDate(today);
        
        updateHeaderLabels(today.getDate());
        loadTasksForDate(today.getDate());

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            updateHeaderLabels(date.getDate());
            loadTasksForDate(date.getDate());
        });

        calendarView.setOnMonthChangedListener((widget, date) -> {
            tvMonthLabel.setText(monthFmt.format(date.getDate()));
        });

        tvMonthLabel.setText(monthFmt.format(new Date()));
    }

    private void setupButtons() {
        findViewById(R.id.btnToday).setOnClickListener(v -> {
            CalendarDay t = CalendarDay.today();
            calendarView.setCurrentDate(t);
            calendarView.setSelectedDate(t);
            updateHeaderLabels(t.getDate());
            loadTasksForDate(t.getDate());
        });

        findViewById(R.id.btnAdd).setOnClickListener(v -> showAddVisitDialog());
    }

    private void updateHeaderLabels(Date selectedDate) {
        tvSelectedDate.setText("Plan na: " + dateFmt.format(selectedDate));
    }

    private void showAddVisitDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_visit, null);
        EditText etTitle = dialogView.findViewById(R.id.etVisitTitle);
        EditText etTime = dialogView.findViewById(R.id.etVisitTime);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Nowa wizyta / zadanie")
                .setView(dialogView)
                .setPositiveButton("Dodaj", (d, w) -> {
                    String title = etTitle.getText().toString();
                    String time = etTime.getText().toString();
                    if (!title.isEmpty()) {
                        addNewTask(title, time);
                    } else {
                        Toast.makeText(this, "Wpisz nazwę", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void addNewTask(String title, String time) {
        if (time.isEmpty()) time = "Cały dzień";
        tasks.add(new DayTask(DayTask.Type.MEASUREMENT, time, title, "Dodano ręcznie", false));
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Dodano do planu", Toast.LENGTH_SHORT).show();
    }

    private void loadTasksForDate(Date selectedDate) {
        tasks.clear();
        // Przykładowe dane stałe
        tasks.add(new DayTask(DayTask.Type.MEDICATION, "08:00", "Tacrolimus", "1 mg • po posiłku", true));
        tasks.add(new DayTask(DayTask.Type.INHALATION, "09:00", "Inhalacja", "Nebulizacja • 10 min", false));
        adapter.notifyDataSetChanged();
    }
}
