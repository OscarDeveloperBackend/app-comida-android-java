package com.senati.app_comida;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    protected void setupBottomNav(int selectedId) {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(selectedId);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == selectedId) return true;

            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (id == R.id.nav_cart) {
                startActivity(new Intent(this, Carrito.class));
            } else if (id == R.id.nav_history) {
                startActivity(new Intent(this, Historial.class));
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(this, Ajustes.class));
            }
            finish();
            return true;
        });
    }
}