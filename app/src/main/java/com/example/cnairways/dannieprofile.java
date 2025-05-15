package com.example.cnairways;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.CompoundButton;



import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class dannieprofile extends AppCompatActivity {
    private String passengerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dannieprofile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent incomingIntent = getIntent();
        String fullName = incomingIntent.getStringExtra("fullName");
        String login = incomingIntent.getStringExtra("login");
        String email = incomingIntent.getStringExtra("email");
        String country = incomingIntent.getStringExtra("country");
        passengerId = incomingIntent.getStringExtra("passengerId");

        EditText editTextFullName = findViewById(R.id.edittextFullName);
        EditText editTextLogin = findViewById(R.id.edittextLogin);
        EditText editTextEmail = findViewById(R.id.edittextEmail);
        EditText editTextCountry = findViewById(R.id.edittextCountry);

        editTextFullName.setText(fullName);
        editTextLogin.setText(login);
        editTextEmail.setText(email);
        editTextCountry.setText(country);
        CheckBox checkBox = findViewById(R.id.checkBoxAgreement);
        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setEnabled(false);
        buttonSave.setAlpha(0.5f);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            buttonSave.setEnabled(isChecked);
            buttonSave.setAlpha(isChecked ? 1.0f : 0.5f);
        });


        buttonSave.setOnClickListener(v -> {
            String updatedFullName = editTextFullName.getText().toString();
            String updatedLogin = editTextLogin.getText().toString();
            String updatedEmail = editTextEmail.getText().toString();
            String updatedCountry = editTextCountry.getText().toString();

            DatabaseReference passengerRef = FirebaseDatabase.getInstance().getReference("passengers").child(passengerId);
            passengerRef.child("fullName").setValue(updatedFullName);
            passengerRef.child("login").setValue(updatedLogin);
            passengerRef.child("email").setValue(updatedEmail);
            passengerRef.child("country").setValue(updatedCountry);

            Toast.makeText(this, "Данные обновлены!", Toast.LENGTH_SHORT).show();


            Intent backToProfileIntent = new Intent(this, profile.class);
            backToProfileIntent.putExtra("passengerId", passengerId);
            startActivity(backToProfileIntent);
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
        findViewById(R.id.buttonticket).setOnClickListener(v -> {
            Intent intent = new Intent(this, ticket.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.settings).setOnClickListener(v -> {
            Intent intent = new Intent(this, settings.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.buttonBack).setOnClickListener(v -> {
            Intent intent = new Intent(this, profile.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }
}