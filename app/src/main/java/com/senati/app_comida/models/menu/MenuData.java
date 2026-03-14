package com.senati.app_comida.models.menu;

import java.util.List;

public class MenuData {

    private List<Food> promotions;
    private List<Category> categories;

    public List<Food> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Food> promotions) {
        this.promotions = promotions;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
