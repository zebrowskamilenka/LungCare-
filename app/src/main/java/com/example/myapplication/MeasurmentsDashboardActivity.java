package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class MeasurmentsDashboardActivity extends AppCompatActivity {

    private TextView tvFev1Value, tvFev1Delta, tvFev1TrendArrow, tvSpo2Value, tvSpo2Status,
            tvPulseValue, tvTempValue, tvTempInfo, tvWeightValue, tvWeightDelta, tvWeightInfo;

    private MaterialCardView cardFev1, cardSpo2, cardPulse, cardTemp, cardWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomiarg);

        bindViews();
        loadAndRenderMvpData();
        setupClicks();
        setupBottomNav();
    }

    private void bindViews() {
        cardFev1 = findViewById(R.id.cardFev1);
        cardSpo2 = findViewById(R.id.cardSpo2);
        cardPulse = findViewById(R.id.cardPulse);
        cardTemp = findViewById(R.id.cardTemp);
        cardWeight = findViewById(R.id.cardWeight);

        tvFev1Value = findViewById(R.id.tvFev1Value);
        tvFev1Delta = findViewById(R.id.tvFev1Delta);
        tvFev1TrendArrow = findViewById(R.id.tvFev1TrendArrow);

        tvSpo2Value = findViewById(R.id.tvSpo2Value);
        tvSpo2Status = findViewById(R.id.tvSpo2Status);

        tvPulseValue = findViewById(R.id.tvPulseValue);

        tvTempValue = findViewById(R.id.tvTempValue);
        tvTempInfo = findViewById(R.id.tvTempInfo);

        tvWeightValue = findViewById(R.id.tvWeightValue);
        tvWeightDelta = findViewById(R.id.tvWeightDelta);
        tvWeightInfo = findViewById(R.id.tvWeightInfo);
    }

    private void loadAndRenderMvpData() {
        // MVP: “ostatnie wartości” — potem podmienisz na Room
        double fev1 = 2.45;          // L
        double fev1DeltaPercent = -12; // vs norma (np. -8 OK, -12 alarm)
        double spo2 = 91;            // %
        int pulse = 72;              // bpm
        double temp = 37.6;          // °C
        double weight = 71.2;        // kg
        double weightDelta3days = 1.2; // kg/3 dni

        // FEV1
        tvFev1Value.setText(String.format(java.util.Locale.getDefault(), "%.2f L", fev1));
        tvFev1Delta.setText(String.format(java.util.Locale.getDefault(), "%+.0f%% vs norma", fev1DeltaPercent));
        tvFev1TrendArrow.setText(trendArrow(fev1DeltaPercent));

        // Kolor FEV1 delta: próg alarmowy np. <= -10%
        setStatusColor(tvFev1Delta, fev1DeltaPercent <= -10);

        // SpO2: próg alarmowy np. < 92%
        tvSpo2Value.setText(String.format(java.util.Locale.getDefault(), "%.0f%%", spo2));
        boolean spo2Alarm = spo2 < 92;
        setValueColor(tvSpo2Value, spo2Alarm);
        tvSpo2Status.setText(spo2Alarm ? "Niska saturacja" : "W normie");

        // Tętno (MVP: bez alarmu, możesz dodać >110)
        tvPulseValue.setText(pulse + "/min");

        // Temperatura: alert od 37.5+
        tvTempValue.setText(String.format(java.util.Locale.getDefault(), "%.1f°C", temp));
        boolean tempAlarm = temp >= 37.5;
        setValueColor(tvTempValue, tempAlarm);
        tvTempInfo.setText(tempAlarm
                ? "Podwyższona — rozważ kontakt z ośrodkiem"
                : "W normie dla osoby na immunosupresji");

        // Waga: alert jeśli +1.0 kg / 3 dni
        tvWeightValue.setText(String.format(java.util.Locale.getDefault(), "%.1f kg", weight));
        tvWeightDelta.setText(String.format(java.util.Locale.getDefault(), "%+.1f kg / 3 dni", weightDelta3days));
        boolean weightAlarm = weightDelta3days >= 1.0;
        setStatusColor(tvWeightDelta, weightAlarm);
        tvWeightInfo.setText(weightAlarm ? "Możliwe zatrzymanie płynów" : "Stabilna masa ciała");
    }

    private void setupClicks() {
        // Tu podepniesz “szczegóły parametru” (historia + wykres)
        cardFev1.setOnClickListener(v ->
                startActivity(new Intent(this, MeasurmentsActivity.class))); // albo Fev1DetailsActivity

        cardSpo2.setOnClickListener(v ->
                startActivity(new Intent(this, MeasurmentsActivity.class)));

        cardPulse.setOnClickListener(v ->
                startActivity(new Intent(this, MeasurmentsActivity.class)));

        cardTemp.setOnClickListener(v ->
                startActivity(new Intent(this, MeasurmentsActivity.class)));

        cardWeight.setOnClickListener(v ->
                startActivity(new Intent(this, MeasurmentsActivity.class)));
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
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

    private String trendArrow(double deltaPercent) {
        if (deltaPercent <= -5) return "↓";
        if (deltaPercent >= 5) return "↑";
        return "→";
    }

    private void setValueColor(TextView tv, boolean alarm) {
        tv.setTextColor(getResources().getColor(alarm ? android.R.color.holo_orange_dark : android.R.color.holo_green_dark));
    }

    private void setStatusColor(TextView tv, boolean alarm) {
        tv.setTextColor(getResources().getColor(alarm ? android.R.color.holo_red_dark : android.R.color.holo_green_dark));
    }
}