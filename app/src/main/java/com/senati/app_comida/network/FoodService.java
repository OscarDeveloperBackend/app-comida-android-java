package com.senati.app_comida.network;

import com.senati.app_comida.models.menu.FoodDetailResponse;
import com.senati.app_comida.models.menu.MenuResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodService {

    @GET("food/{id}")
    Call<FoodDetailResponse> getFoodById(@Path("id") int foodId);

    @GET("food/menu")
    Call<MenuResponse> getMenu();
}
