package com.senati.app_comida.models.menu;

public class MenuResponse {
    private boolean ok;
    private MenuData data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public MenuData getData() {
        return data;
    }

    public void setData(MenuData data) {
        this.data = data;
    }
}
