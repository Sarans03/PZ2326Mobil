package com.example.pz2326;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class OnboardingFragment2 extends Fragment {

    public OnboardingFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding2, container, false);

        TextView tvSkip = view.findViewById(R.id.tv_skip);
        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof OnboardingActivity) {
                    ((OnboardingActivity) getActivity()).skipToLogin();
                }
            }
        });

        return view;
    }
}