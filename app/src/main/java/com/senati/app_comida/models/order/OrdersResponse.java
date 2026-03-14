package com.senati.app_comida.models.order;

import java.util.List;

public class OrdersResponse {
    private boolean ok;
    private List<Order> data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }
}