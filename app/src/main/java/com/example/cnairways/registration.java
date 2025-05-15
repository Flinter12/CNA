package com.example.cnairways;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {
    private DatabaseReference mDataBase;
    private String PASSENGER_KEY = "passengers";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        mDataBase = FirebaseDatabase.getInstance().getReference(PASSENGER_KEY);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.backButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, authorization.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        EditText fullNameEditText = findViewById(R.id.editTextFIO);
        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText countryEditText = findViewById(R.id.editTextCountry);
        EditText loginEditText = findViewById(R.id.editTextLogin);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        Button registerButton = findViewById(R.id.buttonLogin);


        registerButton.setOnClickListener(v -> {

            String fullName = fullNameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String country = countryEditText.getText().toString();
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (fullName.isEmpty() || email.isEmpty() || country.isEmpty() || login.isEmpty() || password.isEmpty()) {
                Toast.makeText(registration.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(registration.this, "Введите корректный email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (fullName.matches(".*\\d.*")) {
                Toast.makeText(registration.this, "ФИО не должно содержать цифры", Toast.LENGTH_SHORT).show();
                return;
            }

            if (country.matches(".*\\d.*")) {
                Toast.makeText(registration.this, "Страна не должна содержать цифры", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 3) {
                Toast.makeText(registration.this, "Пароль должен содержать хотя бы 3 символа", Toast.LENGTH_SHORT).show();
                return;
            }
            String id = mDataBase.push().getKey();

            if (id != null) {

                Passenger passenger = new Passenger(fullName, email, country, login, password, id);

                mDataBase.child(id).setValue(passenger)
                        .addOnSuccessListener(aVoid -> {

                            Toast.makeText(registration.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(registration.this, authorization.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        })
                        .addOnFailureListener(e -> {

                            Toast.makeText(registration.this, "Ошибка регистрации: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(registration.this, "Не удалось создать пользователя", Toast.LENGTH_SHORT).show();
            }
        });
    }
}