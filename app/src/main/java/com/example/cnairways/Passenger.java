package com.example.cnairways;
import java.io.Serializable;
public class Passenger implements Serializable{
    public String fullName;
    public String email;
    public String country;
    public String login;
    public String password;
    public String id;

    // Пустой конструктор для Firebase
    public Passenger() {}

    // Конструктор для создания объекта пользователя
    public Passenger(String fullName, String email, String country, String login, String password, String id) {
        this.fullName = fullName;
        this.email = email;
        this.country = country;
        this.login = login;
        this.password = password;
        this.id = id;
    }

    // Геттеры и сеттеры для каждого поля
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
