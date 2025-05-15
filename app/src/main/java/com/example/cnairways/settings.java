package com.example.cnairways;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

public class settings extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView textFullName, textLogin;
    private String passengerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        passengerId = getIntent().getStringExtra("passengerId");
        textFullName = findViewById(R.id.textFullName);
        textLogin = findViewById(R.id.textLogin);

        mDatabase = FirebaseDatabase.getInstance().getReference("passengers");

        passengerId = getIntent().getStringExtra("passengerId");

        getProfileData(passengerId);

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

        findViewById(R.id.profileverh).setOnClickListener(v -> {
            Intent intent = new Intent(this, profile.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.buttonPrivacy).setOnClickListener(v -> {
            Intent intent = new Intent(this, privacy.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.buttonAbout).setOnClickListener(v -> {
            Intent intent = new Intent(this, company.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.telegram).setOnClickListener(v -> {
            Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
            telegramIntent.setData(android.net.Uri.parse("https://t.me/Flint3r"));
            startActivity(telegramIntent);
        });

        findViewById(R.id.whatsapp).setOnClickListener(v -> {
            Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
            whatsappIntent.setData(android.net.Uri.parse("https://wa.me/+79869067302"));
            startActivity(whatsappIntent);
        });

        findViewById(R.id.vk).setOnClickListener(v -> {
            Intent vkIntent = new Intent(Intent.ACTION_VIEW);
            vkIntent.setData(android.net.Uri.parse("https://vk.com/altf4_flinter"));
            startActivity(vkIntent);
        });

    }

    private void getProfileData(String id) {
        mDatabase.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Passenger passenger = dataSnapshot.getValue(Passenger.class);
                if (passenger != null) {
                    textFullName.setText(passenger.getFullName());
                    textLogin.setText(passenger.getLogin());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(settings.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
