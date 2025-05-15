package com.example.cnairways;

import com.google.firebase.Timestamp;

public class Flight {
    private String flightNumber; // Номер рейса
    private String departureCity; // Город вылета
    private String arrivalCity; // Город прилета
    private String departureTime; // Время вылета
    private String arrivalTime; // Время прилета

    // Пустой конструктор для Firebase
    public Flight() {}

    // Конструктор для создания полета
    public Flight(String flightNumber, String departureCity, String arrivalCity, String departureTime, String arrivalTime) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // Геттеры и сеттеры
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDepartureCity() { return departureCity; }
    public void setDepartureCity(String departureCity) { this.departureCity = departureCity; }

    public String getArrivalCity() { return arrivalCity; }
    public void setArrivalCity(String arrivalCity) { this.arrivalCity = arrivalCity; }

    public String getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }

    public String getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
}