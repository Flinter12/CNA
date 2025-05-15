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

    public class profile extends AppCompatActivity {
        private DatabaseReference mDatabase;
        private TextView textFullName, textLogin, textEmail, textCountry;
        private String passengerId;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_profile);


            textFullName = findViewById(R.id.textFullName);
            textLogin = findViewById(R.id.textLogin);
            textEmail = findViewById(R.id.textEmail);
            textCountry = findViewById(R.id.textCountry);


            mDatabase = FirebaseDatabase.getInstance().getReference("passengers");


            passengerId = getIntent().getStringExtra("passengerId");


            getProfileData(passengerId);


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

            findViewById(R.id.buttonDannie).setOnClickListener(v -> {
                Intent intent = new Intent(this, dannieprofile.class);
                intent.putExtra("fullName", textFullName.getText().toString());
                intent.putExtra("login", textLogin.getText().toString());
                intent.putExtra("email", textEmail.getText().toString());
                intent.putExtra("country", textCountry.getText().toString());
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                        textEmail.setText(passenger.getEmail());
                        textCountry.setText(passenger.getCountry());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(profile.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
