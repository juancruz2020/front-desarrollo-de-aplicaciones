package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Simular carga desde backend
        String tipoUsuario = "alumno"; // puede ser: "visitante", "usuario" o "alumno"

        switch (tipoUsuario) {
            case "visitante":
                setContentView(R.layout.activity_perfil_visitante);
                break;
            case "usuario":
                setContentView(R.layout.activity_perfil_usuario);
                break;
            case "alumno":
                setContentView(R.layout.activity_perfil_alumno);
                break;
        }

        // Botón volver
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}

