package com.example.cnairways;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class My_tickets extends AppCompatActivity {
    private String passengerId;
    private RecyclerView recyclerView;
    private MyTicketsAdapter myTicketsAdapter;
    private List<Tickets> myTickets = new ArrayList<>();
    private DatabaseReference userMyTicketsRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_tickets);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        passengerId = getIntent().getStringExtra("passengerId");

        recyclerView = findViewById(R.id.recyclerViewMyTickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myTicketsAdapter = new MyTicketsAdapter(myTickets);
        recyclerView.setAdapter(myTicketsAdapter);

        userMyTicketsRef = FirebaseDatabase.getInstance()
                .getReference("passengers")
                .child(passengerId)
                .child("myTickets");

        loadMyTickets();
    }
    private void loadMyTickets() {
        userMyTicketsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myTickets.clear();
                for (DataSnapshot ticketSnapshot : snapshot.getChildren()) {
                    Tickets ticket = ticketSnapshot.getValue(Tickets.class);
                    if (ticket != null) {
                        myTickets.add(ticket);
                    }
                }
                myTicketsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(My_tickets.this, "Ошибка загрузки билетов: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}