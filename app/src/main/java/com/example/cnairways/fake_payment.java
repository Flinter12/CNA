package com.example.cnairways;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class fake_payment extends AppCompatActivity {

    EditText cardNumber, expiry, cvc;
    Button payNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fake_payment);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.backlliner).setOnClickListener(v -> {
            Intent intent = new Intent(this, cart.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        cardNumber = findViewById(R.id.cardNumber);
        expiry = findViewById(R.id.expiry);
        cvc = findViewById(R.id.cvc);
        payNowButton = findViewById(R.id.payNowButton);

        payNowButton.setOnClickListener(v -> {
            String card = cardNumber.getText().toString().trim();
            String exp = expiry.getText().toString().trim();
            String code = cvc.getText().toString().trim();


            if (card.isEmpty() || exp.isEmpty() || code.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }


            if (!card.matches("\\d{16}")) {
                Toast.makeText(this, "Номер карты должен содержать 16 цифр", Toast.LENGTH_SHORT).show();
                return;
            }


            if (!exp.matches("(0[1-9]|1[0-2])\\/\\d{2}")) {
                Toast.makeText(this, "Срок действия должен быть в формате MM/YY", Toast.LENGTH_SHORT).show();
                return;
            }


            if (!code.matches("\\d{3}")) {
                Toast.makeText(this, "CVC должен содержать 3 цифры", Toast.LENGTH_SHORT).show();
                return;
            }


            Toast.makeText(this, "Обработка оплаты...", Toast.LENGTH_SHORT).show();
            payNowButton.setEnabled(false);


            new Handler().postDelayed(() -> {
                Toast.makeText(this, "Оплата прошла успешно!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, My_tickets.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }, 2000);
        });
    }
}
