package com.senati.app_comida.models.auth;

public class RegisterRequest {
    private String name;
    private String last_name;
    private String dni;
    private String phone;
    private String email;
    private String location;
    private String password;
    private String password_confirm;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirm() {
        return password_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }

    public RegisterRequest(String name, String last_name, String dni, String phone, String email, String location, String password, String password_confirm) {
        this.name = name;
        this.last_name = last_name;
        this.dni = dni;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.password = password;
        this.password_confirm = password_confirm;
    }
}