package com.senati.app_comida;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.senati.app_comida.models.cart.AddToCartRequest;
import com.senati.app_comida.models.cart.CartItem;
import com.senati.app_comida.models.cart.CartResponse;
import com.senati.app_comida.models.order.OrderResponse;
import com.senati.app_comida.models.order.PlaceOrderRequest;
import com.senati.app_comida.network.ApiClient;
import com.senati.app_comida.network.CartService;
import com.senati.app_comida.network.OrderService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Carrito extends BaseActivity {

    private LinearLayout containerCartItems;
    private TextView txtCartTotal;
    private MaterialButton btnPagar;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        setupBottomNav(R.id.nav_cart);

        containerCartItems = findViewById(R.id.containerCartItems);
        txtCartTotal       = findViewById(R.id.txtCartTotal);
        btnPagar           = findViewById(R.id.btnPagar);

        // obtenemos el id del usuario guardado en SharedPreferences
        userId = getSharedPreferences("user_session", MODE_PRIVATE)
                .getInt("id", 0);

        loadCart();

        btnPagar.setOnClickListener(v -> {
            OrderService orderService = ApiClient.getClient().create(OrderService.class);
            PlaceOrderRequest request = new PlaceOrderRequest(userId);

            orderService.placeOrder(request).enqueue(new Callback<OrderResponse>() {
                @Override
                public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                    if (response.isSuccessful() && response.body().isOk()) {
                        Toast.makeText(Carrito.this, "Pedido confirmado", Toast.LENGTH_SHORT).show();
                        containerCartItems.removeAllViews();
                        txtCartTotal.setText("S/ 0.00");
                    } else {
                        Toast.makeText(Carrito.this, "Error al procesar pedido", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OrderResponse> call, Throwable t) {
                    Toast.makeText(Carrito.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadCart() {
        CartService cartService = ApiClient.getClient().create(CartService.class);

        cartService.getCart(userId).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    containerCartItems.removeAllViews();

                    for (CartItem item : response.body().getData().getItems()) {
                        addCartItem(item);
                    }

                    txtCartTotal.setText("S/ " + response.body().getData().getTotal());
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(Carrito.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCartItem(CartItem item) {
        View card = LayoutInflater.from(this)
                .inflate(R.layout.item_cart, containerCartItems, false);

        ImageView imgFood  = card.findViewById(R.id.imgCartFood);
        TextView txtName   = card.findViewById(R.id.txtCartFoodName);
        TextView txtPrice  = card.findViewById(R.id.txtCartFoodPrice);
        TextView txtQty    = card.findViewById(R.id.txtCartQuantity);
        TextView btnRemove = card.findViewById(R.id.btnRemoveFromCart);

        Glide.with(this).load(item.getUrl_image()).into(imgFood);
        txtName.setText(item.getName());
        txtPrice.setText("S/ " + item.getPrice());
        txtQty.setText("Cantidad: " + item.getQuantity());

        btnRemove.setOnClickListener(v -> removeItem(item.getCart_id()));

        containerCartItems.addView(card);
    }

    private void removeItem(int cartId) {
        CartService cartService = ApiClient.getClient().create(CartService.class);

        cartService.removeFromCart(cartId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Carrito.this, "Eliminado", Toast.LENGTH_SHORT).show();
                    loadCart();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Carrito.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}