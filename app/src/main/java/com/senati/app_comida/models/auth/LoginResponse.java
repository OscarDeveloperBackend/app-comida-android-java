package com.senati.app_comida.models.auth;

public class LoginResponse {
    private boolean ok;
    private UserData data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}