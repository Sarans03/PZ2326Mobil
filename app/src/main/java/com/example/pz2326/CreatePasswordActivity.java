package com.example.pz2326;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreatePasswordActivity extends AppCompatActivity {

    private View indicator1, indicator2, indicator3, indicator4;
    private View[] indicators;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBackspace;
    private TextView tvSkip;

    private StringBuilder password;
    private int passwordLength = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        password = new StringBuilder();

        // Инициализация индикаторов
        indicator1 = findViewById(R.id.indicator_1);
        indicator2 = findViewById(R.id.indicator_2);
        indicator3 = findViewById(R.id.indicator_3);
        indicator4 = findViewById(R.id.indicator_4);

        indicators = new View[]{indicator1, indicator2, indicator3, indicator4};

        // Инициализация кнопок
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btn0 = findViewById(R.id.btn_0);
        btnBackspace = findViewById(R.id.btn_backspace);
        tvSkip = findViewById(R.id.tv_skip);

        // Обработка кнопки "Пропустить"
        tvSkip.setOnClickListener(v -> {
            goToNextScreen();
        });

        // Обработка кнопок с цифрами
        setNumberButton(btn1, "1");
        setNumberButton(btn2, "2");
        setNumberButton(btn3, "3");
        setNumberButton(btn4, "4");
        setNumberButton(btn5, "5");
        setNumberButton(btn6, "6");
        setNumberButton(btn7, "7");
        setNumberButton(btn8, "8");
        setNumberButton(btn9, "9");
        setNumberButton(btn0, "0");

        // Обработка кнопки удаления
        btnBackspace.setOnClickListener(v -> {
            if (password.length() > 0) {
                password.deleteCharAt(password.length() - 1);
                updateIndicators();
            }
        });
    }

    // Метод для обработки кнопок с цифрами
    private void setNumberButton(Button button, final String number) {
        button.setOnClickListener(v -> {
            if (password.length() < passwordLength) {
                password.append(number);
                updateIndicators();

                // Если пароль полностью введён
                if (password.length() == passwordLength) {
                    // Переход на следующий экран
                    goToNextScreen();
                }
            }
        });
    }

    // Обновление индикаторов
    private void updateIndicators() {
        for (int i = 0; i < indicators.length; i++) {
            if (i < password.length()) {
                // Заполненный индикатор (синий)
                indicators[i].setBackgroundResource(R.drawable.circle_indicator_filled);
            } else {
                // Пустой индикатор (белый с рамкой)
                indicators[i].setBackgroundResource(R.drawable.circle_indicator_empty);
            }
        }
    }

    // Переход на следующий экран
    private void goToNextScreen() {
        Intent intent = new Intent(CreatePasswordActivity.this, CreatePatientCardActivity.class);
        startActivity(intent);
        finish();
    }
}