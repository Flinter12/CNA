package com.example.cnairways;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;

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

public class authorization extends AppCompatActivity {
    private DatabaseReference mDataBase;
    private String PASSENGER_KEY = "passengers";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authorization);
        mDataBase = FirebaseDatabase.getInstance().getReference(PASSENGER_KEY);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.textViewRegister).setOnClickListener(v -> {
            Intent intent = new Intent(this, registration.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.textViewForgotPassword).setOnClickListener(v -> {
            Intent intent = new Intent(this, repair_password.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        EditText loginEditText = findViewById(R.id.editTextLogin);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(v -> {
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
            } else {
                checkLoginCredentials(login, password);
            }
        });


    }

    private void checkLoginCredentials(String login, String password) {
        mDataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isAuthenticated = false;
                Passenger authenticatedPassenger = null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Passenger passenger = snapshot.getValue(Passenger.class);
                    if (passenger != null && passenger.getLogin().equals(login) && passenger.getPassword().equals(password)) {
                        isAuthenticated = true;
                        authenticatedPassenger = passenger;

                        break;
                    }
                }

                if (isAuthenticated && authenticatedPassenger != null) {

                    String welcomeMessage = "Добро пожаловать, " + authenticatedPassenger.getFullName() + "!";
                    Toast.makeText(authorization.this, welcomeMessage, Toast.LENGTH_LONG).show();
                    savePassengerIdToPreferences(authenticatedPassenger.getId());

                    Intent intent = new Intent(authorization.this, MainActivity.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Toast.makeText(authorization.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(authorization.this, "Ошибка подключения к базе данных", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void savePassengerIdToPreferences(String passengerId) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("passengerId", passengerId);
        editor.apply(); // Применяем изменения
    }
}