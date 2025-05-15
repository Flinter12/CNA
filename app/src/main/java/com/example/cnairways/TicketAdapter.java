    package com.example.cnairways;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.util.Pair;
    import android.widget.TextView;
    import android.widget.Button;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import android.util.Pair;
    import android.text.TextUtils;
    import android.widget.Toast;
    import android.content.Context;
    import android.content.SharedPreferences;


    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;



    public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
        private List<Pair<Tickets, Flight>> ticketList;

        public TicketAdapter(List<Pair<Tickets, Flight>> ticketList) {
            this.ticketList = ticketList;
        }

        public void updateList(List<Pair<Tickets, Flight>> newList) {
            this.ticketList = newList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
            return new TicketViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
            Tickets ticket = ticketList.get(position).first;
            Flight flight = ticketList.get(position).second;

            holder.routeText.setText(flight.getDepartureCity() + " → " + flight.getArrivalCity());
            holder.dateText.setText("Дата/время вылета: " +flight.getDepartureTime() + " — " + "Дата/время прилета: " + flight.getArrivalTime());
            holder.seatText.setText("Место: " + ticket.getSeatNumber());
            holder.priceText.setText("Цена: " + ticket.getPrice() + "₽");
            holder.classText.setText("Класс: " + ticket.getTicketClass());
            holder.addToCartButton.setOnClickListener(v -> {
                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String passengerId = sharedPreferences.getString("passengerId", null);

                if (passengerId != null) {
                    // Получаем ссылку на корзину пользователя в таблице users
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                            .child("passengers")  // Замените users на passengers
                            .child(passengerId); // Используем users, а не passengers

                    // Проверяем, существует ли поле cart в записи пользователя
                    userRef.child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!snapshot.exists()) {
                                // Если cart не существует, создаем пустой список
                                userRef.child("cart").setValue(new ArrayList<>());
                            }

                            // Добавляем билет в корзину
                            userRef.child("cart").push().setValue(ticket)
                                    .addOnSuccessListener(aVoid ->
                                            Toast.makeText(v.getContext(), "Билет добавлен в корзину", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e ->
                                            Toast.makeText(v.getContext(), "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(v.getContext(), "Ошибка: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(v.getContext(), "Ошибка: ID пользователя не найден", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return ticketList.size();
        }

        static class TicketViewHolder extends RecyclerView.ViewHolder {
            TextView routeText, dateText, seatText, priceText, classText;
            Button addToCartButton;
            public TicketViewHolder(@NonNull View itemView) {
                super(itemView);
                routeText = itemView.findViewById(R.id.routeText);
                dateText = itemView.findViewById(R.id.dateText);
                seatText = itemView.findViewById(R.id.seatText);
                priceText = itemView.findViewById(R.id.priceText);
                classText = itemView.findViewById(R.id.classText);
                addToCartButton = itemView.findViewById(R.id.addToCartButton);
            }
        }
    }
