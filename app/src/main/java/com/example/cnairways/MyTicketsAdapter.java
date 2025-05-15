package com.example.cnairways;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyTicketsAdapter extends RecyclerView.Adapter<MyTicketsAdapter.TicketViewHolder> {

    private List<Tickets> ticketList;

    public MyTicketsAdapter(List<Tickets> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flight_item, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Tickets ticket = ticketList.get(position);
        holder.routeText.setText("Рейс: " + ticket.getFlightId());
        holder.priceText.setText("Цена: " + ticket.getPrice() + "₽");
        holder.classText.setText("Класс: " + ticket.getTicketClass());
        holder.seatText.setText("Место: " + ticket.getSeatNumber());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView routeText, priceText, classText, seatText;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            routeText = itemView.findViewById(R.id.cartRouteText);
            priceText = itemView.findViewById(R.id.cartPriceText);
            classText = itemView.findViewById(R.id.cartClassText);
            seatText = itemView.findViewById(R.id.cartSeatText);
        }
    }
}
