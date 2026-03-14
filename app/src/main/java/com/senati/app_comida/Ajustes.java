package com.senati.app_comida;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Ajustes extends BaseActivity {

    private TextView txtName, txtEmail, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        setupBottomNav(R.id.nav_settings);

        txtName   = findViewById(R.id.txtName);
        txtEmail  = findViewById(R.id.txtEmail);
        btnLogout = findViewById(R.id.btnLogout);

        String name      = getSharedPreferences("user_session", MODE_PRIVATE).getString("name", "");
        String last_name = getSharedPreferences("user_session", MODE_PRIVATE).getString("last_name", "");
        String email     = getSharedPreferences("user_session", MODE_PRIVATE).getString("email", "");

        txtName.setText(name + " " + last_name);
        txtEmail.setText(email);

        btnLogout.setOnClickListener(v -> {
            getSharedPreferences("user_session", MODE_PRIVATE)
                    .edit().clear().apply();
            startActivity(new Intent(this, Login.class));
            finish();
        });
    }
}