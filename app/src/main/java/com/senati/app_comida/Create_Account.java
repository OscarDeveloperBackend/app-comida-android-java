package com.senati.app_comida;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.senati.app_comida.models.auth.RegisterRequest;
import com.senati.app_comida.models.auth.RegisterResponse;
import com.senati.app_comida.network.ApiClient;
import com.senati.app_comida.network.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_Account extends AppCompatActivity {

    private TextInputEditText edtNombre, edtApellido, edtDni;
    private TextInputEditText edtTelefono, edtLocation;
    private TextInputEditText edtEmail, edtPass, edtPassConfi;
    private CheckBox cbTerms;
    private MaterialButton btnCreateAccount;
    private TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        edtNombre    = findViewById(R.id.edtNombre);
        edtApellido  = findViewById(R.id.edtApellido);
        edtDni       = findViewById(R.id.edtDni);
        edtTelefono  = findViewById(R.id.edtTelefono);
        edtLocation  = findViewById(R.id.edtLocation);
        edtEmail     = findViewById(R.id.edtEmail);
        edtPass      = findViewById(R.id.edtPass);
        edtPassConfi = findViewById(R.id.edtPassConfi);
        cbTerms          = findViewById(R.id.cbTerms);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnLogin         = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            finish();
        });

        btnCreateAccount.setOnClickListener(v -> {
            if (validate()) {
                register();
            }
        });
    }

    private boolean validate() {
        String name     = edtNombre.getText().toString().trim();
        String lastName = edtApellido.getText().toString().trim();
        String dni      = edtDni.getText().toString().trim();
        String phone    = edtTelefono.getText().toString().trim();
        String location = edtLocation.getText().toString().trim();
        String email    = edtEmail.getText().toString().trim();
        String pass     = edtPass.getText().toString().trim();
        String passConf = edtPassConfi.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Ingresa tu nombre", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (lastName.isEmpty()) {
            Toast.makeText(this, "Ingresa tu apellido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dni.isEmpty() || dni.length() < 8) {
            Toast.makeText(this, "DNI inválido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.isEmpty()) {
            Toast.makeText(this, "Ingresa tu teléfono", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (location.isEmpty()) {
            Toast.makeText(this, "Ingresa tu dirección", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pass.isEmpty() || pass.length() < 6) {
            Toast.makeText(this, "Mínimo 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!pass.equals(passConf)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Acepta los términos y condiciones", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void register() {
        String name     = edtNombre.getText().toString().trim();
        String lastName = edtApellido.getText().toString().trim();
        String dni      = edtDni.getText().toString().trim();
        String phone    = edtTelefono.getText().toString().trim();
        String location = edtLocation.getText().toString().trim();
        String email    = edtEmail.getText().toString().trim();
        String pass     = edtPass.getText().toString().trim();
        String passConf = edtPassConfi.getText().toString().trim();

        RegisterRequest request = new RegisterRequest(
                name, lastName, dni, phone, email, location, pass, passConf
        );

        AuthService authService = ApiClient.getClient().create(AuthService.class);

        authService.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body().isOk()) {
                    Toast.makeText(Create_Account.this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Create_Account.this, Login.class));
                    finish();
                } else {
                    Toast.makeText(Create_Account.this, "Error al crear cuenta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Create_Account.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}