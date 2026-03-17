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

    private EditText etLogin, etHaslo;
    private CheckBox cbShow;
    private ImageButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin = findViewById(R.id.etLogin);
        etHaslo = findViewById(R.id.etHaslo);
        cbShow = findViewById(R.id.cbShow);
        loginButton = findViewById(R.id.loginButton);

        cbShow.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etHaslo.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etHaslo.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etHaslo.setSelection(etHaslo.getText().length());
        });

        loginButton.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String haslo = etHaslo.getText().toString().trim();

            if (!login.isEmpty() && !haslo.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, PanelActivity.class);
                // PRZEKAZUJEMY LOGIN DO NASTĘPNEGO EKRANU
                intent.putExtra("USER_LOGIN", login);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Proszę podać login i hasło", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
