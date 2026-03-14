package com.senati.app_comida.network;

import com.senati.app_comida.models.menu.MenuResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodService {
    @GET("food/menu")
    Call<MenuResponse> getMenu();
}
