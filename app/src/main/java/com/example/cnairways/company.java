package com.example.cnairways;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class company extends AppCompatActivity {

    private String passengerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_company);
        passengerId = getIntent().getStringExtra("passengerId");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.buttonticket).setOnClickListener(v -> {
            Intent intent = new Intent(this, ticket.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (id == R.id.search) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);

                Intent intent = new Intent(this, search.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            else if (id == R.id.cart) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);

                Intent intent = new Intent(this, cart.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            else if (id == R.id.profile) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);

                Intent intent = new Intent(this, profile.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }

            return false;
        });
        findViewById(R.id.settings).setOnClickListener(v -> {
            Intent intent = new Intent(this, settings.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.profileverh).setOnClickListener(v -> {
            Intent intent = new Intent(this, profile.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.buttonBack).setOnClickListener(v -> {
            Intent intent = new Intent(this, settings.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}