package com.example.myapplication;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MedicationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications_details);

        TextView tvName = findViewById(R.id.tvMedName);
        TextView tvDose = findViewById(R.id.tvMedDose);
        TextView tvTime = findViewById(R.id.tvMedTime);
        TextView tvInfo = findViewById(R.id.tvMedInfo);

        String name = getIntent().getStringExtra("name");
        String dose = getIntent().getStringExtra("dose");
        String time = getIntent().getStringExtra("time");
        String info = getIntent().getStringExtra("info");

        if (name != null) tvName.setText(name);
        if (dose != null) tvDose.setText("Dawka: " + dose);
        if (time != null) tvTime.setText("Godzina: " + time);

        if (info == null || info.trim().isEmpty()) {
            info = "Brak dodatkowych informacji.";
        }
        tvInfo.setText(info);
    }
}
