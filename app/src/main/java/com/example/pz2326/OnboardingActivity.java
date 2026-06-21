package com.example.pz2326;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private OnboardingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);
        adapter = new OnboardingAdapter(this);
        viewPager.setAdapter(adapter);
    }

    // Переход к LoginActivity
    public void skipToLogin() {
        Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}