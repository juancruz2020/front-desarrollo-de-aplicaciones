package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.conection.ApiService;
import com.example.myapplication.dto.RegistroDatosPersonalesDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroUsuario extends AppCompatActivity {

    private EditText nombreUsuario, apellidoUsuario, edad, dni, contra, confirmarContra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        nombreUsuario = findViewById(R.id.nombreUsuario);
        apellidoUsuario = findViewById(R.id.apellidoUsuario);
        edad = findViewById(R.id.edad);
        dni = findViewById(R.id.dni);
        contra = findViewById(R.id.contra);
        confirmarContra = findViewById(R.id.confirmarContra);
    }

    public void volverARegistro(View view) {
        finish();
    }

    public void inicio(View view) {
        Toast.makeText(this, "Ir a inicio de sesión", Toast.LENGTH_SHORT).show();
    }

    public void registrarse(View view) {
        String nombre = nombreUsuario.getText().toString().trim();
        String apellido = apellidoUsuario.getText().toString().trim();
        String edadStr = edad.getText().toString().trim();
        String dniStr = dni.getText().toString().trim();
        String password = contra.getText().toString();
        String confirmarPass = confirmarContra.getText().toString();

        // Validaciones
        if (TextUtils.isEmpty(nombre)) {
            nombreUsuario.setError("El nombre es obligatorio");
            return;
        }
        if (TextUtils.isEmpty(apellido)) {
            apellidoUsuario.setError("El apellido es obligatorio");
            return;
        }
        if (TextUtils.isEmpty(edadStr)) {
            edad.setError("La edad es obligatoria");
            return;
        }
        if (TextUtils.isEmpty(dniStr)) {
            dni.setError("El DNI es obligatorio");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            contra.setError("La contraseña es obligatoria");
            return;
        }
        if (!password.equals(confirmarPass)) {
            confirmarContra.setError("Las contraseñas no coinciden");
            return;
        }

        int edadInt;
        int dniInt;
        try {
            edadInt = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            edad.setError("La edad debe ser un número");
            return;
        }
        try {
            dniInt = Integer.parseInt(dniStr);
        } catch (NumberFormatException e) {
            dni.setError("El DNI debe ser un número");
            return;
        }
        SharedPreferences preferences = getSharedPreferences("mis_preferencias", MODE_PRIVATE);
        String emailGuardado = preferences.getString("email", "");

        // Crear DTO para enviar al backend
        RegistroDatosPersonalesDTO dto = new RegistroDatosPersonalesDTO( emailGuardado,  password,  nombre,  apellido,  Integer.parseInt(dniStr),  Integer.parseInt(edadStr));


        // Llamada a API
        ApiService apiService = ApiClient.getInstance().getApiService();
        Call<ResponseBody> call = apiService.registrarDatosPersonales(dto);

        // Mostrar mensaje de carga (opcional)

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroUsuario.this, "Registro exitoso", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(RegistroUsuario.this, "Error en registro: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegistroUsuario.this, "Fallo en conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
