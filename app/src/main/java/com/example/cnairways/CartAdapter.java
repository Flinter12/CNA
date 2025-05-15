package com.example.cnairways;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Tickets> ticketList;
    private List<String> ticketKeys; // Ключи для удаления из Firebase
    private String passengerId;

    public CartAdapter(Context context, List<Tickets> ticketList, List<String> ticketKeys, String passengerId) {
        this.context = context;
        this.ticketList = ticketList;
        this.ticketKeys = ticketKeys;
        this.passengerId = passengerId;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_ticket, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Tickets ticket = ticketList.get(position);

        holder.routeText.setText("Номер рейса: " + ticket.getFlightId());
        holder.seatText.setText("Место: " + ticket.getSeatNumber());
        holder.priceText.setText("Цена: " + ticket.getPrice() + "₽");
        holder.classText.setText("Класс: " + ticket.getTicketClass());

        holder.removeButton.setOnClickListener(v -> {
            String key = ticketKeys.get(position);
            DatabaseReference cartRef = FirebaseDatabase.getInstance()
                    .getReference("passengers")
                    .child(passengerId)
                    .child("cart")
                    .child(key);

            cartRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "Билет удалён", Toast.LENGTH_SHORT).show();
                        ticketList.remove(position);
                        ticketKeys.remove(position);
                        notifyItemRemoved(position);
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
        holder.pay_button.setOnClickListener(v -> {
            String key = ticketKeys.get(position);
            DatabaseReference passengerRef = FirebaseDatabase.getInstance()
                    .getReference("passengers")
                    .child(passengerId);

            // Добавляем билет в раздел "MyTickets"
            passengerRef.child("myTickets").child(key).setValue(ticket)
                    .addOnSuccessListener(aVoid -> {
                        // Удаляем билет из корзины после успешной оплаты
                        DatabaseReference cartRef = FirebaseDatabase.getInstance()
                                .getReference("passengers")
                                .child(passengerId)
                                .child("cart")
                                .child(key);

                        cartRef.removeValue()
                                .addOnSuccessListener(aVoid1 -> {
                                    Toast.makeText(context, "Билет оплачен и перемещен в Мои билеты", Toast.LENGTH_SHORT).show();
                                    ticketList.remove(position);
                                    ticketKeys.remove(position);
                                    notifyItemRemoved(position);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(context, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Ошибка при оплате: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView routeText, seatText, priceText, classText;
        Button removeButton;
        Button pay_button;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            routeText = itemView.findViewById(R.id.cartRouteText);
            seatText = itemView.findViewById(R.id.cartSeatText);
            priceText = itemView.findViewById(R.id.cartPriceText);
            classText = itemView.findViewById(R.id.cartClassText);
            removeButton = itemView.findViewById(R.id.removeFromCartButton);
            pay_button = itemView.findViewById(R.id.pay_button);
        }
    }
}
