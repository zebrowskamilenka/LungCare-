package com.example.myapplication;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ImageButton;

public class PanelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        LinearLayout cardDzienniczek = findViewById(R.id.cardDzienniczek);
        LinearLayout cardLeki = findViewById(R.id.cardVademecum);
        LinearLayout cardEdukacja = findViewById(R.id.cardLeki);
        LinearLayout cardMapa = findViewById(R.id.cardMapa);
        ImageButton btnCalendar = findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(PanelActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
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