package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.text.InputType;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Najpierw ładujemy layout!
        setContentView(R.layout.activity_main);

        // 2. Dopiero potem pobieramy widoki
        EditText etHaslo = findViewById(R.id.etHaslo);
        CheckBox cbShow = findViewById(R.id.cbShow);
        Button btn = findViewById(R.id.loginButton);

        cbShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etHaslo.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etHaslo.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etHaslo.setSelection(etHaslo.getText().length());
        });

        btn.setOnClickListener(v -> {
            String login = ((EditText) findViewById(R.id.etLogin)).getText().toString().trim();
            String haslo = etHaslo.getText().toString();

            if (login.isEmpty()) {
                ((EditText) findViewById(R.id.etLogin)).setError("Podaj login");
                return;
            }
            if (haslo.isEmpty()) {
                etHaslo.setError("Podaj hasło");
                return;
            }
            startActivity(new Intent(MainActivity.this, PanelActivity.class));
            // TODO: przejście dalej
            // startActivity(new Intent(this, NextActivity.class));
        });
    }
}
