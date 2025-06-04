package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.conection.ApiService;
import com.example.myapplication.dto.CambioContrasenaDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarContra2 extends AppCompatActivity {

    EditText codigoInput, contraNuevaInput, contraNueva2Input;

    SharedPreferences prefs;
    String mailGuardado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_recuperar_contra2);

        // Inicializar SharedPreferences dentro de onCreate
        prefs = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        mailGuardado = prefs.getString("user_mail", "");  // "" si no existe

        codigoInput = findViewById(R.id.codigo);
        contraNuevaInput = findViewById(R.id.contrasena_nueva);
        contraNueva2Input = findViewById(R.id.contrasena_nueva2);
    }

    public void volver(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void iniciarSesion(View view) {
        // Leer datos de los EditText
        String codigo = codigoInput.getText().toString().trim();
        String contraNueva = contraNuevaInput.getText().toString().trim();
        String contraNueva2 = contraNueva2Input.getText().toString().trim();

        // Validaciones básicas
        if (codigo.isEmpty() || contraNueva.isEmpty() || contraNueva2.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contraNueva.equals(contraNueva2)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Llamar al servicio para cambiar contraseña
        ApiService apiService = ApiClient.getInstance().getApiService();
        CambioContrasenaDTO dto = new CambioContrasenaDTO(mailGuardado, codigo, contraNueva);

        apiService.cambiarContrasena(dto).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RecuperarContra2.this, "Contraseña cambiada con éxito", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RecuperarContra2.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RecuperarContra2.this, "Error al cambiar la contraseña", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RecuperarContra2.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
