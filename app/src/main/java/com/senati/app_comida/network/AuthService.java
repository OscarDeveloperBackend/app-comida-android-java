package com.senati.app_comida.network;

import com.senati.app_comida.models.auth.LoginRequest;
import com.senati.app_comida.models.auth.LoginResponse;
import com.senati.app_comida.models.auth.RegisterRequest;
import com.senati.app_comida.models.auth.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("user")
    Call<RegisterResponse> register(@Body RegisterRequest request);
}