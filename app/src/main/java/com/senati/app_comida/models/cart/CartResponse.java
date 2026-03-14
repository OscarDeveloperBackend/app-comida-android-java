package com.senati.app_comida.models.cart;

public class CartResponse {
    private boolean ok;
    private CartData data;


    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public CartData getData() {
        return data;
    }

    public void setData(CartData data) {
        this.data = data;
    }
}