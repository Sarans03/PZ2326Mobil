package com.example.pz2326;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VerificationCodeActivity extends AppCompatActivity {

    private EditText etCode1, etCode2, etCode3, etCode4;
    private TextView tvTimer;
    private ImageView ivBack;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnBackspace;

    private int currentField = 0;
    private EditText[] codeFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        // Инициализация полей для кода
        etCode1 = findViewById(R.id.et_code_1);
        etCode2 = findViewById(R.id.et_code_2);
        etCode3 = findViewById(R.id.et_code_3);
        etCode4 = findViewById(R.id.et_code_4);

        codeFields = new EditText[]{etCode1, etCode2, etCode3, etCode4};

        tvTimer = findViewById(R.id.tv_timer);
        ivBack = findViewById(R.id.iv_back);

        // Инициализация кнопок клавиатуры
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

        // Обработка стрелки назад
        ivBack.setOnClickListener(v -> {
            finish(); // Возврат на предыдущий экран
        });

        // Обработка кнопок цифровой клавиатуры
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
            if (currentField > 0) {
                currentField--;
                codeFields[currentField].setText("");
                codeFields[currentField].requestFocus();
            }
        });

        // Запуск таймера
        startTimer();
    }

    // Метод для обработки кнопок с цифрами
    private void setNumberButton(Button button, String number) {
        button.setOnClickListener(v -> {
            if (currentField < 4) {
                codeFields[currentField].setText(number);
                currentField++;

                // Если все поля заполнены, проверяем код
                if (currentField == 4) {
                    checkCode();
                }
            }
        });
    }

    // Проверка кода (в методичке не указан конкретный код, ставим 1234)
    private void checkCode() {
        String code = etCode1.getText().toString() +
                etCode2.getText().toString() +
                etCode3.getText().toString() +
                etCode4.getText().toString();

        // Проверка на соответствие (например, код 1234)
        if (code.equals("1234")) {
            // Переход на экран "Создание пароля"

            Intent intent = new Intent(VerificationCodeActivity.this, CreatePasswordActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Неверный код! Попробуйте 1234", Toast.LENGTH_SHORT).show();
        }
    }

    // Таймер обратного отсчёта
    private void startTimer() {
        new CountDownTimer(60000, 1000) { // 60 секунд
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                tvTimer.setText("Отправить код повторно можно будет через " + seconds + " секунд");
            }

            public void onFinish() {
                tvTimer.setText("Отправить код повторно можно будет через 0 секунд");
            }
        }.start();
    }
}