// AddToCartRequest.java
package com.senati.app_comida.models.cart;

public class AddToCartRequest {
    private int user_id;
    private int food_id;

    public AddToCartRequest(int user_id, int food_id) {
        this.user_id = user_id;
        this.food_id = food_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }
}