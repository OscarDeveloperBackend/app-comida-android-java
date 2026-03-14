package com.senati.app_comida;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senati.app_comida.models.order.Order;
import com.senati.app_comida.models.order.OrderItem;
import com.senati.app_comida.models.order.OrdersResponse;
import com.senati.app_comida.network.ApiClient;
import com.senati.app_comida.network.OrderService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Historial extends BaseActivity {

    private TextView txtOrderId,txtOrderStatus,txtOrderDate,txtOrderTotal;
    private LinearLayout containerOrderItems;
    private LinearLayout containerOrders;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        setupBottomNav(R.id.nav_history);

        containerOrders = findViewById(R.id.containerOrders);

        userId = getSharedPreferences("user_session", MODE_PRIVATE)
                .getInt("id", 0);

        loadOrders();
    }

    private void loadOrders() {
        OrderService orderService = ApiClient.getClient().create(OrderService.class);

        orderService.getOrders(userId).enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Call<OrdersResponse> call, Response<OrdersResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    containerOrders.removeAllViews();

                    if (response.body().getData().isEmpty()) {
                        Toast.makeText(Historial.this, "No tienes pedidos aún", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int total = response.body().getData().size();
                    int contador = total;

                    for (Order order : response.body().getData()) {
                        addOrderCard(order, contador);
                        contador--;
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersResponse> call, Throwable t) {
                Toast.makeText(Historial.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addOrderCard(Order order, int numero) {
        View card = LayoutInflater.from(this)
                .inflate(R.layout.item_order, containerOrders, false);


        TextView txtOrderId       = card.findViewById(R.id.txtOrderId);
        TextView txtOrderStatus   = card.findViewById(R.id.txtOrderStatus);
        TextView txtOrderDate     = card.findViewById(R.id.txtOrderDate);
        TextView txtOrderTotal    = card.findViewById(R.id.txtOrderTotal);
        LinearLayout containerItems = card.findViewById(R.id.containerOrderItems);

        txtOrderId.setText("Pedido #" + numero);
        txtOrderStatus.setText(order.getStatus());
        txtOrderDate.setText(order.getCreated_at());
        txtOrderTotal.setText("S/ " + order.getTotal());

        for (OrderItem item : order.getItems()) {
            addOrderItemRow(item, containerItems);
        }

        containerOrders.addView(card);
    }

    private void addOrderItemRow(OrderItem item, LinearLayout container) {
        View row = LayoutInflater.from(this)
                .inflate(R.layout.item_order_food, container, false);

        ImageView imgFood     = row.findViewById(R.id.imgOrderFood);
        TextView txtName      = row.findViewById(R.id.txtOrderFoodName);
        TextView txtQty       = row.findViewById(R.id.txtOrderFoodQty);
        TextView txtPrice     = row.findViewById(R.id.txtOrderFoodPrice);

        Glide.with(this).load(item.getUrl_image()).into(imgFood);
        txtName.setText(item.getName());
        txtQty.setText("x" + item.getQuantity());
        txtPrice.setText("S/ " + item.getPrice());

        container.addView(row);
    }
}