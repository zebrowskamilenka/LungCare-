package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Demo loginy/hasło na sztywno
    private static final String LOGIN_1 = "admin";
    private static final String LOGIN_2 = "login";
    private static final String PASSWORD = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etLogin = findViewById(R.id.etLogin);
        EditText etHaslo = findViewById(R.id.etHaslo);
        CheckBox cbShow = findViewById(R.id.cbShow);
        ImageButton btn = findViewById(R.id.loginButton);

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
            String haslo = etHaslo.getText().toString();

            // Walidacja pustych pól
            if (login.isEmpty()) {
                etLogin.setError("Podaj login");
                etLogin.requestFocus();
                return;
            }
            if (haslo.isEmpty()) {
                etHaslo.setError("Podaj hasło");
                etHaslo.requestFocus();
                return;
            }

            // Sprawdzenie na sztywno
            boolean loginOk = login.equals(LOGIN_1);
            boolean passOk = haslo.equals(PASSWORD);

            if (!loginOk || !passOk) {
                Toast.makeText(this, "Nieprawidłowy login lub hasło", Toast.LENGTH_SHORT).show();
                etHaslo.setText("");
                etHaslo.requestFocus();
                return;
            }

            // Przejście do PanelActivity
            Intent intent = new Intent(MainActivity.this, PanelActivity.class);
            intent.putExtra("user_login", login); // opcjonalnie, jak chcesz wyświetlić w panelu
            startActivity(intent);
            finish(); // opcjonalnie: usuwa ekran logowania z historii (żeby back nie wracał)
        });
    }
}
