package com.example.pz2326;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private Button btnLogout;
    private TextView tvFullName, tvBirthDate, tvPatientInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Инициализация элементов
        btnLogout = view.findViewById(R.id.btn_logout);
        tvFullName = view.findViewById(R.id.tv_full_name);
        tvBirthDate = view.findViewById(R.id.tv_birth_date);
        tvPatientInfo = view.findViewById(R.id.tv_patient_info);

        // Обработка кнопки "Выйти"
        btnLogout.setOnClickListener(v -> {
            // Возврат на экран входа с очисткой стека активности
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }
}