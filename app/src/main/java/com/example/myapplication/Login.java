package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.conection.ApiService;
import com.example.myapplication.dto.LoginDTO;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText mail, contra;
    CheckBox recordarme;
    TextView notifContra;
    int intentos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);

        mail = findViewById(R.id.mail);
        contra = findViewById(R.id.contra);
        recordarme = findViewById(R.id.recuerdame);
        notifContra = findViewById(R.id.olvidasteContra);

        // ✅ Verificar si ya hay datos guardados
        SharedPreferences preferences = getSharedPreferences("mis_preferencias", MODE_PRIVATE);
        boolean recordado = preferences.getBoolean("recordarme", false);

        if (recordado) {
            String emailGuardado = preferences.getString("email", "");
            String passGuardada = preferences.getString("password", "");
            mail.setText(emailGuardado);
            contra.setText(passGuardada);
            recordarme.setChecked(true);

            // Auto-login directo
            iniciarSesion(null); // Pasamos null porque no usamos el botón directamente
        }
    }

    public void iniciarSesion(View view) {
         
        String email = mail.getText().toString().trim();
        String password = contra.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getInstance().getApiService();
        LoginDTO loginDTO = new LoginDTO(email, email, password);

        apiService.login(loginDTO).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        if (intentos <= 5) {
                            String mensaje = response.body().string();

                            SharedPreferences preferences = getSharedPreferences("mis_preferencias", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            if (recordarme.isChecked()) {
                                editor.putString("email", email);
                                editor.putString("password", password);
                                editor.putBoolean("recordarme", true);
                            } else {
                                editor.clear(); // Borrar si no quiere recordar
                            }
                            editor.apply();

                            Toast.makeText(Login.this, "Login exitoso: " + mensaje, Toast.LENGTH_LONG).show();

                            // Redireccionar después del login
                            Intent intent = new Intent(Login.this, InicioActivity.class); // O la clase que uses
                            startActivity(intent);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Login.this, "Error al leer respuesta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    manejarIntentoFallido();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Login.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void manejarIntentoFallido() {
        intentos++;
        if (intentos <= 5) {
            contra.setError("Contraseña incorrecta. Intento " + intentos + " de 5");
        } else {
            SpannableString spannable = new SpannableString("Excediste el límite de intentos! \nOlvidaste tu contraseña? ve a Recuperar contraseña");
            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#E25683")), 33, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            notifContra.setText(spannable);
            notifContra.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Demasiados intentos fallidos", Toast.LENGTH_LONG).show();
        }
    }

    public void irARecuperarContrasena(View view) {
        Intent intent = new Intent(Login.this, RecuperarContra.class);
        startActivity(intent);
    }

    public void irACrearCuenta(View view) {
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
    }
}
