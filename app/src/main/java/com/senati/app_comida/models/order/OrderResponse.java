package com.senati.app_comida.models.order;

public class OrderResponse {
    private boolean ok;
    private Order data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }
}