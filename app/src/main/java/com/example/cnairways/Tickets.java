package com.example.cnairways;

import com.google.firebase.database.PropertyName;

public class Tickets {
    private String ticketId;
    private String flightId;
    private String seatNumber;
    private String price;
    private String ticketClass;

    public Tickets() {}

    public Tickets(String ticketId, String flightId, String seatNumber, String price, String ticketClass) {
        this.ticketId = ticketId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.ticketClass = ticketClass;
    }

    @PropertyName("ticketID")
    public String getTicketId() {
        return ticketId;
    }

    @PropertyName("ticketID")
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    @PropertyName("flightID")
    public String getFlightId() {
        return flightId;
    }

    @PropertyName("flightID")
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }
}
