package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Simular carga desde backend
        String tipoUsuario = "alumno"; // puede ser: "visitante", "usuario" o "alumno"

        Intent cerrarSesion = new Intent(this, Login.class);

        switch (tipoUsuario) {
            case "visitante":
                setContentView(R.layout.activity_perfil_visitante);
                //iniciarSesion
                Intent inicioSesion = new Intent(this, Login.class);
                findViewById(R.id.btnIniciarSesion).setOnClickListener( v -> startActivity(inicioSesion));
                //crearCuenta
                Intent crearCuenta = new Intent(this, Registro.class);
                findViewById(R.id.btnCrearCuenta).setOnClickListener(v -> startActivity(crearCuenta));
                break;
            case "usuario":
                setContentView(R.layout.activity_perfil_usuario);
                //pasar de Usuario a Alumno
                Intent usuarioAlumno = new Intent(this, RegistroAlumno.class);
                findViewById(R.id.btnConvertirseAlumno).setOnClickListener(v -> startActivity(usuarioAlumno));
                findViewById(R.id.btnCerrarSesion).setOnClickListener(v -> startActivity(cerrarSesion));

                break;
            case "alumno":
                setContentView(R.layout.activity_perfil_alumno);
                findViewById(R.id.btnCerrarSesion).setOnClickListener(v -> startActivity(cerrarSesion));
                break;
        }

        // Botón volver
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    // Editar Tarjeta
        Button btnEditar = findViewById(R.id.tvEditarNumeroTarjeta);
        btnEditar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.editar_tarjeta, null);
            builder.setView(dialogView);

            builder.setPositiveButton("Guardar", (dialog, which) -> {
                EditText etNumero = dialogView.findViewById(R.id.etNumeroTarjeta);
                String nuevoNumero = etNumero.getText().toString();
                // actualizar TextView u operar con el dato
                TextView tvNumero = findViewById(R.id.tvNumeroTarjeta);
                tvNumero.setText("**** **** **** " + nuevoNumero.substring(nuevoNumero.length()-4));
            });

            builder.setNegativeButton("Cancelar", null);

            builder.create().show();
        });

}

