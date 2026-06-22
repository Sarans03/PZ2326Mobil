package com.example.pz2326;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnNext, btnYandex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        btnNext = findViewById(R.id.btn_next);
        btnYandex = findViewById(R.id.btn_yandex);

        // Слушатель для поля Email
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Не нужно
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Проверяем, заполнено ли поле
                if (s.toString().trim().isEmpty()) {
                    // Поле пустое - делаем кнопку неактивной
                    btnNext.setEnabled(false);
                    btnNext.setBackgroundResource(R.drawable.background_button_inactive);
                } else {
                    // Поле заполнено - делаем кнопку активной
                    btnNext.setEnabled(true);
                    btnNext.setBackgroundResource(R.drawable.background_button_active);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Обработка кнопки "Далее"
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, VerificationCodeActivity.class);
            startActivity(intent);
        });

        // Обработка кнопки "Войти с Яндекс"
        btnYandex.setOnClickListener(v -> {
            // Тут будет авторизация через Яндекс
        });
    }
}