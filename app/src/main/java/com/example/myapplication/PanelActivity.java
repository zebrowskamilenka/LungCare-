package com.example.myapplication;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.EducationFragment;
import com.example.myapplication.VademecumActivity;
import com.example.myapplication.ui.gallery.MedicationFragment;

public class PanelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        LinearLayout cardDzienniczek = findViewById(R.id.cardDzienniczek);
        LinearLayout cardLeki = findViewById(R.id.cardVademecum);
        LinearLayout cardEdukacja = findViewById(R.id.cardLeki);
        LinearLayout cardMapa = findViewById(R.id.cardMapa);
        cardMapa.setOnClickListener(v -> {
            Intent intent = new Intent(PanelActivity.this, MapsActivity.class);
            startActivity(intent);
        });
        cardDzienniczek.setOnClickListener(v ->
                startActivity(new Intent(PanelActivity.this, DiaryActivity.class))
        );
        cardLeki.setOnClickListener(v ->
                startActivity(new Intent(PanelActivity.this, VademecumActivity.class))
        );

        // leki
        cardEdukacja.setOnClickListener(v ->
                startActivity(new Intent(PanelActivity.this, MedicationActivity.class))
        );

    }
}