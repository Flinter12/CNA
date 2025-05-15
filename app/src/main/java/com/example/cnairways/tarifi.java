package com.example.cnairways;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class tarifi extends AppCompatActivity {
    private AutoCompleteTextView searchEditText;
    private Button searchButton;
    private String passengerId;
    private List<String> pagesList = Arrays.asList("Профиль", "Настройки", "Спецпредложения", "Сервисы", "Информация", "Купить билет", "Онлайн-табло","Направления и рейсы", "О покупке и оплате", "Дополнительные услуги","Лучшие тарифы", "Подарочный сертификат", "Тарифы для детей", "Тарифы для студентов", "Грузовые перевозки","Поддержка", "Правовая информация", "На борту", "В аэропорту", "Подготовка к путешествию", "Конфиденциальность", "О компании", "Корзина", "Мои билеты");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarifi);
        passengerId = getIntent().getStringExtra("passengerId");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, pagesList);
        searchEditText.setAdapter(adapter);

        searchButton.setOnClickListener(v -> {
            String searchText = searchEditText.getText().toString().trim();

            if (!searchText.isEmpty()) {
                searchPages(searchText);
            } else {
                Toast.makeText(this, "Введите текст для поиска", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.buttonInfo).setOnClickListener(v -> {
            Intent intent = new Intent(this, information.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        findViewById(R.id.buttonService).setOnClickListener(v -> {
            Intent intent = new Intent(this, services.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        findViewById(R.id.buttonSpec).setOnClickListener(v -> {
            Intent intent = new Intent(this, specialoffers.class);
            intent.putExtra("passengerId", passengerId);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        findViewById(R.id.buttonticket).setOnClickListener(v -> {
            Intent intent = new Intent(this, ticket.class);
            intent.putExtra("passengerId", passengerId);
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
    }
    private void searchPages(String searchQuery) {
        List<String> searchResults = new ArrayList<>();
        for (String page : pagesList) {
            if (page.toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(page);
            }
        }
        if (!searchResults.isEmpty()) {
            displaySearchResults(searchResults);
        } else {
            showNoResultsFound();
        }
    }
    private void displaySearchResults(List<String> results) {

        for (String result : results) {
            if (result.equals("Профиль")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, profile.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (result.equals("Настройки")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, settings.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Спецпредложения")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, specialoffers.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Сервисы")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, services.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Информация")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, information.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Онлайн-табло")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, onlinetablo.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Направления и рейсы")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, napravlenia.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("О покупке и оплате")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, oplata.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Дополнительные услуги")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, dop.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Лучшие тарифы")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, tarifi.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Подарочный сертификат")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, sertificat.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Тарифы для детей")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, kids.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Тарифы для студентов")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, students.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Грузовые перевозки")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, perevozki.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Поддержка")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, podderzhka.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Правовая информация")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, pravo.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("На борту")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, bort.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("В аэропорту")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, airport.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Подготовка к путешествию")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, podgotovka.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Конфиденциальность")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, privacy.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("О компании")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, company.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Корзина")) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);
                Intent intent = new Intent(this, cart.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    }

    private void showNoResultsFound() {
        Toast.makeText(this, "Страница не найдена", Toast.LENGTH_SHORT).show();
    }
}


