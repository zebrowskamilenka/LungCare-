package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MedicationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications_details);

        TextView tvName = findViewById(R.id.tvMedName);
        TextView tvIngredients = findViewById(R.id.tvIngredients);
        TextView tvDose = findViewById(R.id.tvMedDose);
        TextView tvInfo = findViewById(R.id.tvMedInfo);
        MaterialButton btnBack = findViewById(R.id.btnBack);

        // Odbieramy obiekt leku z Intenta
        MedicationActivity.Medication med = (MedicationActivity.Medication) getIntent().getSerializableExtra("med_object");

        if (med != null) {
            tvName.setText(med.name);
            tvIngredients.setText(med.ingredients);
            tvDose.setText(med.dosage);
            tvInfo.setText(med.notes);
        }

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }
}
