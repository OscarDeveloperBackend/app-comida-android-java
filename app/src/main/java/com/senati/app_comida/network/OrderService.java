package com.senati.app_comida.network;

import com.senati.app_comida.models.order.OrderResponse;
import com.senati.app_comida.models.order.PlaceOrderRequest;
import com.senati.app_comida.models.order.OrdersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderService {

    @GET("orders")
    Call<OrdersResponse> getOrders(@Query("user_id") int userId);

    @POST("orders")
    Call<OrderResponse> placeOrder(@Body PlaceOrderRequest request);
}
