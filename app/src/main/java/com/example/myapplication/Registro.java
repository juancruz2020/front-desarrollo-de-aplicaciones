package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.conection.ApiService;
import com.example.myapplication.dto.RegistroUsuarioDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registro extends AppCompatActivity {

    private Button btnUsuario;
    private Button btnAlumno;
    private String tipoSeleccionado = ""; // "usuario" o "alumno"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnUsuario = findViewById(R.id.btn_usuario);
        btnAlumno = findViewById(R.id.btn_alumno);
    }

    public void btn_volver(View view) {
        finish();
    }

    public void btnVolverInicio(View view) {
        Toast.makeText(this, "Volviendo al inicio de sesión...", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void seleccionarUsuario(View view) {
        tipoSeleccionado = "usuario";
        btnUsuario.setBackgroundResource(R.drawable.btn_ok);
        btnAlumno.setBackgroundResource(R.drawable.btn_off);
        Toast.makeText(this, "Seleccionaste Usuario", Toast.LENGTH_SHORT).show();
    }

    public void seleccionarAlumno(View view) {
        tipoSeleccionado = "alumno";
        btnAlumno.setBackgroundResource(R.drawable.btn_ok);
        btnUsuario.setBackgroundResource(R.drawable.btn_off);
        Toast.makeText(this, "Seleccionaste Alumno", Toast.LENGTH_SHORT).show();
    }

    public void verificarDatos(View view) {
        EditText mail = findViewById(R.id.mail);
        EditText alias = findViewById(R.id.ingreseAlias);
        TextView notif = findViewById(R.id.notifText1);

        String emailText = mail.getText().toString().trim();
        String aliasText = alias.getText().toString().trim();


        if (emailText.isEmpty() || aliasText.isEmpty() || tipoSeleccionado.isEmpty()) {
            notif.setVisibility(View.VISIBLE);
            notif.setText("Todos los campos son obligatorios.");
            return;
        }

        notif.setVisibility(View.INVISIBLE);

        RegistroUsuarioDTO dto = new RegistroUsuarioDTO(emailText, aliasText);

        ApiService apiService = ApiClient.getInstance().getApiService();
        Call<ResponseBody> call = apiService.registrarUsuario(dto);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registro.this, Codigo.class);
                    intent.putExtra("tipoUsuario", tipoSeleccionado);
                    startActivity(intent);
                    finish();
                } else {
                    notif.setVisibility(View.VISIBLE);
                    notif.setText("Error en el registro. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                notif.setVisibility(View.VISIBLE);
                notif.setText("Fallo de conexión: " + t.getMessage());
            }
        });
    }
}