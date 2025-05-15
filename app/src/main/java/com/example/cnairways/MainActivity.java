package com.example.cnairways;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView searchEditText;
    private Button searchButton;

    private List<String> pagesList = Arrays.asList("Профиль", "Настройки", "Спецпредложения", "Сервисы", "Информация", "Купить билет", "Онлайн-табло","Направления и рейсы", "О покупке и оплате", "Дополнительные услуги","Лучшие тарифы", "Подарочный сертификат", "Тарифы для детей", "Тарифы для студентов", "Грузовые перевозки","Поддержка", "Правовая информация", "На борту", "В аэропорту", "Подготовка к путешествию", "Конфиденциальность", "О компании", "Корзина", "Мои билеты");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String passengerId = sharedPreferences.getString("passengerId", null);

        // If passengerId is null, handle the case (maybe log the user out or show an error)
        if (passengerId == null) {
            Toast.makeText(this, "Ошибка: не найден пользователь", Toast.LENGTH_SHORT).show();
            // Optionally redirect the user to the login screen
            return;
        }
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


                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            } else if (id == R.id.search) {


                Intent intent = new Intent(this, search.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            else if (id == R.id.cart) {


                Intent intent = new Intent(this, cart.class);
                intent.putExtra("passengerId", passengerId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            }
            else if (id == R.id.profile) {


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
                startActivity(new Intent(this, profile.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (result.equals("Настройки")) {
                startActivity(new Intent(this, settings.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Спецпредложения")) {
                startActivity(new Intent(this, specialoffers.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Сервисы")) {
                startActivity(new Intent(this, services.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Информация")) {
                startActivity(new Intent(this, information.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Онлайн-табло")) {
                startActivity(new Intent(this, onlinetablo.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Направления и рейсы")) {
                startActivity(new Intent(this, napravlenia.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("О покупке и оплате")) {
                startActivity(new Intent(this, oplata.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Дополнительные услуги")) {
                startActivity(new Intent(this, dop.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Лучшие тарифы")) {
                startActivity(new Intent(this, tarifi.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Подарочный сертификат")) {
                startActivity(new Intent(this, sertificat.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Тарифы для детей")) {
                startActivity(new Intent(this, kids.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Тарифы для студентов")) {
                startActivity(new Intent(this, students.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Грузовые перевозки")) {
                startActivity(new Intent(this, perevozki.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Поддержка")) {
                startActivity(new Intent(this, podderzhka.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Правовая информация")) {
                startActivity(new Intent(this, pravo.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("На борту")) {
                startActivity(new Intent(this, bort.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("В аэропорту")) {
                startActivity(new Intent(this, airport.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Подготовка к путешествию")) {
                startActivity(new Intent(this, podgotovka.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Конфиденциальность")) {
                startActivity(new Intent(this, privacy.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("О компании")) {
                startActivity(new Intent(this, company.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if (result.equals("Корзина")) {
                startActivity(new Intent(this, cart.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
    }

    private void showNoResultsFound() {
        Toast.makeText(this, "Страница не найдена", Toast.LENGTH_SHORT).show();
    }

}

