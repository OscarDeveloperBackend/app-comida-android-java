package com.senati.app_comida;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senati.app_comida.models.cart.AddToCartRequest;
import com.senati.app_comida.models.menu.Category;
import com.senati.app_comida.models.menu.Food;
import com.senati.app_comida.models.menu.MenuResponse;
import com.senati.app_comida.network.ApiClient;
import com.senati.app_comida.network.CartService;
import com.senati.app_comida.network.FoodService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private LinearLayout containerPromotions;
    private LinearLayout containerCategories;
    private TextView txtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        containerPromotions = findViewById(R.id.containerPromotions);
        containerCategories = findViewById(R.id.containerCategories);
        txtUserName         = findViewById(R.id.txtUserName);

        String name = getSharedPreferences("user_session", MODE_PRIVATE)
                .getString("name", "");
        txtUserName.setText("Hola, " + name);
        setupBottomNav(R.id.nav_home);

        loadMenu();
    }

    private void loadMenu() {
        FoodService foodService = ApiClient.getClient().create(FoodService.class);

        foodService.getMenu().enqueue(new Callback<MenuResponse>() {
            @Override
            public void onResponse(Call<MenuResponse> call, Response<MenuResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Food food : response.body().getData().getPromotions()) {
                        addPromoCard(food);
                    }
                    for (Category category : response.body().getData().getCategories()) {
                        addCategorySection(category);
                    }
                }
            }

            @Override
            public void onFailure(Call<MenuResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPromoCard(Food food) {
        View card = LayoutInflater.from(this)
                .inflate(R.layout.item_promotion, containerPromotions, false);

        ImageView imgPromotion = card.findViewById(R.id.imgPromotion);
        TextView txtName       = card.findViewById(R.id.txtPromoName);
        TextView txtPrice      = card.findViewById(R.id.txtPromoPrice);

        Glide.with(this).load(food.getUrl_image()).into(imgPromotion);
        txtName.setText(food.getName());
        txtPrice.setText("S/ " + food.getPrice());

        containerPromotions.addView(card);
    }

    private void addCategorySection(Category category) {
        View section = LayoutInflater.from(this)
                .inflate(R.layout.item_category, containerCategories, false);

        TextView txtCategoryName    = section.findViewById(R.id.txtCategoryName);
        LinearLayout containerFoods = section.findViewById(R.id.containerFoods);

        txtCategoryName.setText(category.getName());

        for (Food food : category.getFoods()) {
            addFoodCard(food, containerFoods);
        }

        containerCategories.addView(section);
    }

    private void addFoodCard(Food food, LinearLayout container) {
        View card = LayoutInflater.from(this)
                .inflate(R.layout.item_food, container, false);

        ImageView imgFood = card.findViewById(R.id.imgFood);
        TextView txtName  = card.findViewById(R.id.txtFoodName);
        TextView txtPrice = card.findViewById(R.id.txtFoodPrice);
        TextView txtStock = card.findViewById(R.id.txtFoodStock);

        Glide.with(this).load(food.getUrl_image()).into(imgFood);
        txtName.setText(food.getName());
        txtPrice.setText("S/ " + food.getPrice());
        txtStock.setText("Stock: " + food.getStock());

        card.findViewById(R.id.btnAddToCart).setOnClickListener(v -> {
            CartService cartService = ApiClient.getClient().create(CartService.class);
            int userId = getSharedPreferences("user_session", MODE_PRIVATE).getInt("id", 0);

            AddToCartRequest request = new AddToCartRequest(userId, food.getId());

            cartService.addToCart(request).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, food.getName() + " agregado al carrito", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        container.addView(card);
    }
}