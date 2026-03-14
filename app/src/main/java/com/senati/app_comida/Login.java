package com.senati.app_comida;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.senati.app_comida.models.auth.LoginRequest;
import com.senati.app_comida.models.auth.LoginResponse;
import com.senati.app_comida.models.auth.UserData;
import com.senati.app_comida.network.ApiClient;
import com.senati.app_comida.network.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    private TextInputEditText edtEmail, edtPassword;
    private TextInputLayout tilEmail, tilPassword;
    private MaterialButton btnLogin;
    private TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        if (prefs.getInt("id", 0) != 0) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail    = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tilEmail    = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        btnLogin    = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);



        txtRegister.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Create_Account.class));
        });

        btnLogin.setOnClickListener(v -> {
            if (verificate()) {
                String email    = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // crear el request
                AuthService authService = ApiClient.getClient().create(AuthService.class);
                LoginRequest request    = new LoginRequest(email, password);

                authService.login(request).enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            UserData user = response.body().getData();

                            SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("id",        user.getId());
                            editor.putString("name",   user.getName());
                            editor.putString("last_name", user.getLast_name());
                            editor.putString("email", user.getEmail() );
                            editor.putInt("role_id",   user.getRole_id());
                            editor.apply();

                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(Login.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(Login.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean verificate() {
        String email    = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        tilEmail.setError(null);
        tilPassword.setError(null);

        if (email.isEmpty()) {
            tilEmail.setError("Ingresa tu email");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Email inválido");
            return false;
        }

        if (password.isEmpty()) {
            tilPassword.setError("Ingresa tu contraseña");
            return false;
        }

        return true;
    }
}