package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.conection.ApiService;
import com.example.myapplication.dto.MailCodigoDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarContra extends AppCompatActivity {

    ImageButton btnVolver;
    EditText editTextMail;

    // Nombre del archivo SharedPreferences
    private static final String PREFS_NAME = "MyAppPrefs";
    // Clave para guardar el mail
    private static final String KEY_MAIL = "user_mail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_recuperar_contra);

        btnVolver = findViewById(R.id.btn_volver);
        editTextMail = findViewById(R.id.mail);
    }

    public void volver(View view) {
        finish();
    }

    public void enviarCodigoRecuperacion(View view) {
        String mail = editTextMail.getText().toString().trim();

        if (mail.isEmpty()) {
            Toast.makeText(this, "Por favor ingresá tu correo", Toast.LENGTH_SHORT).show();
            return;
        }

        MailCodigoDto dto = new MailCodigoDto(mail);
        ApiService apiService = ApiClient.getInstance().getApiService();
        Call<ResponseBody> call = apiService.cambiarContrasenaCodigo(dto);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Guardar el mail en SharedPreferences
                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(KEY_MAIL, mail);
                    editor.apply(); // o commit()

                    Toast.makeText(RecuperarContra.this, "Código enviado a tu mail", Toast.LENGTH_LONG).show();
                    runOnUiThread(() -> irACodigo());
                } else {
                    Toast.makeText(RecuperarContra.this, "No se pudo enviar el código", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RecuperarContra.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void irACodigo() {
        Intent intent = new Intent(RecuperarContra.this, RecuperarContra2.class);
        startActivity(intent);
    }
    public void irALogin(View view) {
        Intent intent = new Intent(RecuperarContra.this, Login.class);
        startActivity(intent);
    }

}
