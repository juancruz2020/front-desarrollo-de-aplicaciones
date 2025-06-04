package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.conection.ApiService;
import com.example.myapplication.dto.CodigoDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Codigo extends AppCompatActivity {

    private EditText codigoEditText;
    private String tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_codigo);

        codigoEditText = findViewById(R.id.codigo);

        // Obtener el tipo de usuario guardado en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("mis_preferencias", MODE_PRIVATE);
        tipoUsuario = prefs.getString("tipoUsuario", "");

        Toast.makeText(this, "Tipo seleccionado: " + tipoUsuario, Toast.LENGTH_SHORT).show();
    }

    public void volver(View view) {
        finish();
    }

    public void volverInicio(View view) {
        Toast.makeText(this, "Volviendo al inicio de sesión...", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void validarcodigo(View view) {

        SharedPreferences preferences = getSharedPreferences("mis_preferencias", MODE_PRIVATE);
        String emailGuardado = preferences.getString("email", ""); // Segundo parámetro es valor por defecto si no existe
        String codigo = codigoEditText.getText().toString().trim();

        if (emailGuardado.isEmpty() || codigo.isEmpty()) {
            Toast.makeText(this, "Por favor, completá todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        CodigoDTO dto = new CodigoDTO(emailGuardado, codigo);

        ApiService apiService = ApiClient.getInstance().getApiService();
        Call<ResponseBody> call = apiService.validarCodigo(dto);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Codigo.this, "Código válido. ¡Bienvenido!", Toast.LENGTH_LONG).show();

                    Intent intent = getIntent();
                    String tipoUsuario = intent.getStringExtra("tipoUsuario");
                    if (tipoUsuario.equals("usuario")){
                        startActivity(new Intent(Codigo.this, RegistroUsuario.class));
                    }
                    else if (tipoUsuario.equals("alumno")){
                        startActivity(new Intent(Codigo.this, RegistroAlumno.class));
                    }

                    finish();
                } else {
                    Toast.makeText(Codigo.this, "Código inválido o expirado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Codigo.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
