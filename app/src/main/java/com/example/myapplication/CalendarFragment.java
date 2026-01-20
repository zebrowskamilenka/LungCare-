package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class CalendarFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private TextView tvSelectedDate;
    private RecyclerView rvDayTasks;

    private DayTaskAdapter adapter;

    public CalendarFragment() {
        super(R.layout.fragment_calendar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.materialCalendar);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        rvDayTasks = view.findViewById(R.id.rvDayTasks);

        adapter = new DayTaskAdapter(new DayTaskAdapter.Listener() {
            @Override
            public void onTaskChecked(DayTask task, boolean checked) {
                // TODO: tu zapis do bazy Room
                // Na start pokażmy tylko komunikat:
                Toast.makeText(requireContext(),
                        (checked ? "Odhaczone: " : "Cofnięte: ") + task.title,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTaskAction(DayTask task) {
                // TODO: np. przejście do szczegółów wizyty albo Mapy UCK
                Toast.makeText(requireContext(),
                        "Akcja: " + task.title,
                        Toast.LENGTH_SHORT).show();
            }
        });

        rvDayTasks.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvDayTasks.setAdapter(adapter);

        // Ustaw domyślnie dzisiejszą datę
        CalendarDay today = CalendarDay.today();
        calendarView.setSelectedDate(today);
        updateSelectedDateText(today);
        adapter.setItems(fakeTasksForDay(today));

        // Kliknięcie dnia
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            updateSelectedDateText(date);

            // TODO: tu docelowo pobierasz z Room: tasks = repository.getTasks(date)
            adapter.setItems(fakeTasksForDay(date));
        });
    }

    private void updateSelectedDateText(CalendarDay date) {
        // Proste formatowanie: 2026-01-20
        String formatted = String.format(Locale.getDefault(),
                "%04d-%02d-%02d",
                date.getYear(), date.getMonth(), date.getDay());

        tvSelectedDate.setText("Plan dnia – " + formatted);
    }

    // Przykładowe dane (żebyś widział od razu efekt)
    private List<DayTask> fakeTasksForDay(CalendarDay date) {
        List<DayTask> list = new ArrayList<>();

        list.add(new DayTask(DayTask.Type.MEDICATION, "Tacrolimus", "Dawka: 2 mg", "08:00", false));
        list.add(new DayTask(DayTask.Type.INHALATION, "Inhalacja", "Nebulizacja", "09:00", false));
        list.add(new DayTask(DayTask.Type.MEASUREMENT, "Saturacja", "Wpisz SpO₂", "12:00", false));

        // Co kilka dni pokaż wizytę (tylko jako przykład)
        if (date.getDay() % 5 == 0) {
            list.add(new DayTask(DayTask.Type.VISIT, "Wizyta kontrolna", "UCK – Poradnia", "14:30", false));
        }

        // Dzienniczek wieczorem
        list.add(new DayTask(DayTask.Type.DIARY, "Dzienniczek objawów", "Szybki check-in", "19:00", false));

        return list;
    }
}