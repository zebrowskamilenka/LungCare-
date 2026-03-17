package com.example.myapplication;

import android.graphics.Color;
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
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class CalendarActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private TextView tvMonthLabel, tvSelectedDate;
    private RecyclerView rvDayTasks;
    private DayTaskAdapter adapter;
    
    private final Map<String, List<DayTask>> taskDatabase = new HashMap<>();
    private final List<DayTask> currentDayTasks = new ArrayList<>();
    private final HashSet<CalendarDay> eventDays = new HashSet<>();

    private final SimpleDateFormat monthFmt = new SimpleDateFormat("MMMM yyyy", new Locale("pl", "PL"));
    private final SimpleDateFormat dateFmt = new SimpleDateFormat("d MMMM yyyy", new Locale("pl", "PL"));
    private final SimpleDateFormat dbKeyFmt = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bindViews();
        setupRecyclerView();
        setupCalendar();
        setupButtons();
        
        loadTasksForDate(new Date());
    }

    private void bindViews() {
        calendarView = findViewById(R.id.calendarView);
        tvMonthLabel = findViewById(R.id.tvMonthLabel);
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        rvDayTasks = findViewById(R.id.rvDayTasks);
    }

    private void setupRecyclerView() {
        rvDayTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DayTaskAdapter(currentDayTasks, (pos, done) -> {});
        rvDayTasks.setAdapter(adapter);
    }

    private void setupCalendar() {
        CalendarDay today = CalendarDay.today();
        calendarView.setSelectedDate(today);
        calendarView.setCurrentDate(today);
        
        tvMonthLabel.setText(monthFmt.format(new Date()));
        updateHeaderLabels(today.getDate());

        // Dodajemy dekorator raz - będzie korzystał z dynamicznego zbioru eventDays
        calendarView.addDecorator(new EventDecorator(Color.RED, eventDays));

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            updateHeaderLabels(date.getDate());
            loadTasksForDate(date.getDate());
        });

        calendarView.setOnMonthChangedListener((widget, date) -> {
            tvMonthLabel.setText(monthFmt.format(date.getDate()));
        });
    }

    private void setupButtons() {
        View btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

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
                        saveNewTask(title, time, calendarView.getSelectedDate());
                    } else {
                        Toast.makeText(this, "Wpisz nazwę zadania", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void saveNewTask(String title, String time, CalendarDay day) {
        if (time.isEmpty()) time = "Cały dzień";
        
        DayTask newTask = new DayTask(DayTask.Type.MEASUREMENT, time, title, "Dodano przez pacjenta", false);
        
        String key = dbKeyFmt.format(day.getDate());
        List<DayTask> dayList = taskDatabase.get(key);
        if (dayList == null) {
            dayList = new ArrayList<>();
            taskDatabase.put(key, dayList);
        }
        dayList.add(newTask);
        
        // Dodajemy datę do zbioru i wymuszamy odświeżenie dekoratorów
        eventDays.add(day);
        calendarView.invalidateDecorators();
        
        loadTasksForDate(day.getDate());
        Toast.makeText(this, "Dodano do planu", Toast.LENGTH_SHORT).show();
    }

    private void loadTasksForDate(Date selectedDate) {
        currentDayTasks.clear();
        String key = dbKeyFmt.format(selectedDate);
        
        currentDayTasks.add(new DayTask(DayTask.Type.MEDICATION, "08:00", "Tacrolimus", "1 mg • rano", false));
        
        List<DayTask> userTasks = taskDatabase.get(key);
        if (userTasks != null) {
            currentDayTasks.addAll(userTasks);
        }
        
        adapter.notifyDataSetChanged();
        
        TextView tvNoTasks = findViewById(R.id.tvNoTasks);
        if (tvNoTasks != null) {
            tvNoTasks.setVisibility(currentDayTasks.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }


    private static class EventDecorator implements DayViewDecorator {
        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, HashSet<CalendarDay> dates) {
            this.color = color;
            this.dates = dates; // Referencja do tego samego zbioru co w Activity
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(10, color)); // Nieco większa kropka (10 zamiast 8)
        }
    }
}
