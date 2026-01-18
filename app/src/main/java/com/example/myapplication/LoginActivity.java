package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Twój ekran logowania

        EditText etLogin = findViewById(R.id.etLogin);
        EditText etHaslo = findViewById(R.id.etHaslo);
        CheckBox cbShow = findViewById(R.id.cbShow);
        Button btn = findViewById(R.id.loginButton);

        cbShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etHaslo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etHaslo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etHaslo.setSelection(etHaslo.getText().length());
        });

        btn.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String haslo = etHaslo.getText().toString().trim();

            if (login.isEmpty()) {
                etLogin.setError("Podaj login!");
                return;
            }
            if (haslo.isEmpty()) {
                etHaslo.setError("Podaj hasło!");
                return;
            }

            // PRZEJŚCIE DO GŁÓWNEJ CZĘŚCI APLIKACJI (z menu i fragmentami)
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });
    }
}