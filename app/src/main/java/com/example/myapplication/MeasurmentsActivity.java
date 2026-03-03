package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MeasurmentsActivity extends AppCompatActivity {

    private TextView tvDetailsTitle, tvHistory;
    private LineChart lineChart;

    private String type = "FEV1"; // domyślnie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomiary_details); // <-- DOPASUJ nazwę layoutu

        tvDetailsTitle = findViewById(R.id.tvDetailsTitle);
        tvHistory = findViewById(R.id.tvHistory);
        lineChart = findViewById(R.id.lineChart);

        readTypeFromIntent();
        setupTitle();
        setupBottomNav();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getTitle(type));
        } else {
            setTitle(getTitle(type));
        }
        renderChart(getSampleSeries(type), getLabel(type));
        renderHistory(getSampleSeries(type), getUnit(type));
    }

    private void readTypeFromIntent() {
        Intent i = getIntent();
        if (i != null && i.hasExtra("TYPE")) {
            type = i.getStringExtra("TYPE");
            if (type == null) type = "FEV1";
        }
    }

    private void setupTitle() {
        tvDetailsTitle.setText(getTitle(type));
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        if (bottomNav == null) return;

        bottomNav.setSelectedItemId(R.id.nav_measures);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_start) {
                startActivity(new Intent(this, PanelActivity.class));
                return true;
            } else if (id == R.id.nav_leki) {
                startActivity(new Intent(this, MedicationActivity.class));
                return true;
            } else if (id == R.id.nav_measures) {
                // jesteś w pomiarach – możesz wrócić do dashboardu:
                startActivity(new Intent(this, MeasurmentsDashboardActivity.class));
                return true;
            } else if (id == R.id.nav_vademecum) {
                startActivity(new Intent(this, VademecumActivity.class));
                return true;
            } else if (id == R.id.nav_contact) {
                startActivity(new Intent(this, ContactActivity.class));
                return true;
            }
            return false;
        });
    }

    // -----------------------
    // Wykres
    // -----------------------
    private void renderChart(List<Float> values, String label) {
        if (lineChart == null) return;

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setDrawGridLines(true);

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            entries.add(new Entry(i, values.get(i)));
        }

        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircles(true);
        dataSet.setLineWidth(2f);

        lineChart.setData(new LineData(dataSet));
        lineChart.invalidate();
    }

    // -----------------------
    // Historia (MVP jako tekst)
    // -----------------------
    private void renderHistory(List<Float> values, String unit) {
        if (tvHistory == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append("Ostatnie pomiary:\n\n");

        // MVP: udajemy dni wstecz
        for (int i = values.size() - 1; i >= 0; i--) {
            int dayAgo = (values.size() - 1) - i;
            sb.append(String.format(Locale.getDefault(),
                    "D-%d  →  %.2f %s\n",
                    dayAgo,
                    values.get(i),
                    unit
            ));
        }

        tvHistory.setText(sb.toString());
    }

    // -----------------------
    // Dane MVP
    // -----------------------
    private List<Float> getSampleSeries(String t) {
        List<Float> vals = new ArrayList<>();

        switch (t) {
            case "SPO2":
                float[] spo2 = {96, 97, 96, 95, 96, 94, 93, 95, 96, 96, 97, 96, 95, 96};
                for (float v : spo2) vals.add(v);
                break;

            case "PULSE":
                float[] pulse = {72, 74, 76, 73, 70, 71, 78, 80, 77, 74, 72, 71, 73, 75};
                for (float v : pulse) vals.add(v);
                break;

            case "TEMP":
                float[] temp = {36.7f, 36.8f, 36.9f, 37.0f, 36.8f, 36.7f, 37.2f, 37.6f, 37.0f, 36.9f, 36.8f, 36.7f, 36.9f, 36.8f};
                for (float v : temp) vals.add(v);
                break;

            case "WEIGHT":
                float baseW = 70.0f;
                for (int i = 0; i < 14; i++) vals.add((float) (baseW + i * 0.08));
                break;

            case "FEV1":
            default:
                float base = 2.55f;
                for (int i = 0; i < 14; i++) vals.add((float) (base - i * 0.02));
                break;
        }

        return vals;
    }

    // -----------------------
    // Opisy
    // -----------------------
    private String getTitle(String t) {
        switch (t) {
            case "SPO2": return "Szczegóły: SpO₂";
            case "PULSE": return "Szczegóły: Tętno";
            case "TEMP": return "Szczegóły: Temperatura";
            case "WEIGHT": return "Szczegóły: Waga";
            case "FEV1":
            default: return "Szczegóły: FEV₁";
        }
    }

    private String getLabel(String t) {
        switch (t) {
            case "SPO2": return "SpO₂ (%)";
            case "PULSE": return "Tętno (bpm)";
            case "TEMP": return "Temperatura (°C)";
            case "WEIGHT": return "Waga (kg)";
            case "FEV1":
            default: return "FEV₁ (L)";
        }
    }

    private String getUnit(String t) {
        switch (t) {
            case "SPO2": return "%";
            case "PULSE": return "bpm";
            case "TEMP": return "°C";
            case "WEIGHT": return "kg";
            case "FEV1":
            default: return "L";
        }
    }
}