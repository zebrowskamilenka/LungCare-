package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class PanelActivity extends AppCompatActivity {
    @Override

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.menu_activity);

    MaterialCardView cardDzienniczek = findViewById(R.id.cardDzienniczek);
    MaterialCardView cardVademecum = findViewById(R.id.cardVademecum);
    MaterialCardView cardLeki = findViewById(R.id.cardLeki);
    MaterialCardView cardMapa = findViewById(R.id.cardMapa);
    MaterialCardView cardContact = findViewById(R.id.cardContact);
    MaterialCardView cardCalendar=findViewById(R.id.cardCalendar);

    cardDzienniczek.setOnClickListener(v ->
            startActivity(new Intent(PanelActivity.this, DiaryActivity.class))
    );

    cardVademecum.setOnClickListener(v ->
            startActivity(new Intent(PanelActivity.this, VademecumActivity.class))
    );

    cardLeki.setOnClickListener(v ->
            startActivity(new Intent(PanelActivity.this, MedicationActivity.class))
    );

    cardContact.setOnClickListener(v ->
            startActivity(new Intent(PanelActivity.this, ContactActivity.class))
    );
        cardCalendar.setOnClickListener(v ->
                startActivity(new Intent(PanelActivity.this, CalendarActivity.class))
        );}
}