package com.example.pz2326;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreatePatientCardActivity extends AppCompatActivity {

    private EditText etFirstName, etMiddleName, etLastName, etBirthDate;
    private Spinner spinnerGender;
    private Button btnCreate;
    private TextView tvSkip;

    private String selectedGender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient_card);

        // Инициализация полей
        etFirstName = findViewById(R.id.et_first_name);
        etMiddleName = findViewById(R.id.et_middle_name);
        etLastName = findViewById(R.id.et_last_name);
        etBirthDate = findViewById(R.id.et_birth_date);
        spinnerGender = findViewById(R.id.spinner_gender);
        btnCreate = findViewById(R.id.btn_create);
        tvSkip = findViewById(R.id.tv_skip);

        // Настройка Spinner (выпадающий список "Пол")
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.spinner_gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = parent.getItemAtPosition(position).toString();
                checkFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGender = "";
            }
        });

        // Слушатели для полей ввода
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etFirstName.addTextChangedListener(textWatcher);
        etMiddleName.addTextChangedListener(textWatcher);
        etLastName.addTextChangedListener(textWatcher);
        etBirthDate.addTextChangedListener(textWatcher);

        // Обработка кнопки "Пропустить"
        tvSkip.setOnClickListener(v -> {
            goToMainActivity();
        });

        // Обработка кнопки "Создать"
        btnCreate.setOnClickListener(v -> {
            goToMainActivity();
        });
    }

    // Проверка заполнения всех полей
    private void checkFields() {
        String firstName = etFirstName.getText().toString().trim();
        String middleName = etMiddleName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String birthDate = etBirthDate.getText().toString().trim();

        boolean allFilled = !firstName.isEmpty() &&
                !middleName.isEmpty() &&
                !lastName.isEmpty() &&
                !birthDate.isEmpty() &&
                !selectedGender.isEmpty() &&
                !selectedGender.equals("Выберите пол");

        if (allFilled) {
            btnCreate.setEnabled(true);
            btnCreate.setBackgroundResource(R.drawable.background_button_active);
        } else {
            btnCreate.setEnabled(false);
            btnCreate.setBackgroundResource(R.drawable.background_button_inactive);
        }
    }

    // Переход на главный экран
    private void goToMainActivity() {

        Toast.makeText(this, "Карта пациента создана!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CreatePatientCardActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}