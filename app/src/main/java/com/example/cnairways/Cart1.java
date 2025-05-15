package com.example.cnairways;

import java.util.ArrayList;
import java.util.List;

public class Cart1 {
    private List<Tickets> tickets = new ArrayList<>(); // Список билетов в корзине

    // Метод для добавления билета в корзину
    public void addTicket(Tickets ticket) {
        tickets.add(ticket);
    }

    // Метод для удаления билета из корзины
    public void removeTicket(Tickets ticket) {
        tickets.remove(ticket);
    }

    // Получение всех билетов в корзине
    public List<Tickets> getTickets() {
        return tickets;
    }
}
