package com.senati.app_comida.models.order;

public class PlaceOrderRequest {
    private int user_id;

    public PlaceOrderRequest(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
}