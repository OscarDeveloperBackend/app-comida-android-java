package com.senati.app_comida.network;

import com.senati.app_comida.models.cart.AddToCartRequest;
import com.senati.app_comida.models.cart.CartResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartService {

    @GET("cart")
    Call<CartResponse> getCart(@Query("user_id") int userId);

    @POST("cart")
    Call<Void> addToCart(@Body AddToCartRequest request);

    @DELETE("cart/{id}")
    Call<Void> removeFromCart(@Path("id") int cartId);
}