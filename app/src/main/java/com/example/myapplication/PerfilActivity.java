package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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



        // Editar Perfil
        Button btnEditarPerfil = findViewById(R.id.btnEditarPerfil);
        if (btnEditarPerfil != null) {
        btnEditarPerfil.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.editar_perfil, null);
            builder.setView(dialogView);

            // Capturar referencias a los EditText del dialog
            EditText etAlias = dialogView.findViewById(R.id.etAlias);
            EditText etNombre = dialogView.findViewById(R.id.etNombre);
            EditText etMail = dialogView.findViewById(R.id.etMail);
            EditText etDNI = dialogView.findViewById(R.id.etDNI);

            // Mostrar el dialog
            builder.setPositiveButton("Guardar", (dialog, which) -> {
                // Obtener los valores ingresados
                String alias = etAlias.getText().toString();
                String nombre = etNombre.getText().toString();
                String mail = etMail.getText().toString();
                String dni = etDNI.getText().toString();

                // Actualizar los TextView del perfil
                TextView tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
                TextView tvNombreCompleto = findViewById(R.id.tvNombreCompleto);
                TextView tvEmail = findViewById(R.id.tvEmail);
                TextView tvDNI = findViewById(R.id.tvDNI);

                if (!alias.isEmpty()) tvNombreUsuario.setText(alias);
                if (!nombre.isEmpty()) tvNombreCompleto.setText(nombre);
                if (!mail.isEmpty()) tvEmail.setText(mail);
                if (!dni.isEmpty()) tvDNI.setText(dni);
            });

            builder.setNegativeButton("Cancelar", null);

            builder.create().show();
        });
        }




        // Editar Tarjeta
        Button btnEditar = findViewById(R.id.tvEditarNumeroTarjeta);
        if (btnEditar != null) {
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

        // Terminos y condiciones / soporte
        btnTerminos = findViewById(R.id.btnTerminos);
        if (btnTerminos != null) {
            btnTerminos.setOnClickListener(v -> {
                Intent intent = new Intent(this, TerminosYCondiciones.class);
                startActivity(intent);
            });
        }

        btnContacto = findViewById(R.id.btnContacto);
        if (btnContacto != null) {
            btnContacto.setOnClickListener(v -> {
                Intent intent = new Intent(this, TerminosYCondiciones.class);
                startActivity(intent);
            });
        }

    }

}

