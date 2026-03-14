package com.senati.app_comida.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    Produccion
    private static final String BASE_URL = "https://api-tienda-comida.onrender.com/";

//    Dev
//    private static final String BASE_URL = "http://192.168.18.31:3000/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}