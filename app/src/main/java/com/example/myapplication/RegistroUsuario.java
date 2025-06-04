package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroUsuario extends AppCompatActivity {

    ImageButton btnVolver; EditText nombre, apellido, edad, contra, confirContra, dniNum; Button inicio_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_registro_usuario);

        btnVolver = findViewById(R.id.btn_volver);
        nombre = findViewById(R.id.nombreUsuario);
        apellido = findViewById(R.id.apellidoUsuario);
        edad = findViewById(R.id.edad);
        contra = findViewById(R.id.contra);
        confirContra = findViewById(R.id.confirmarContra);
        dniNum = findViewById(R.id.dni);
        inicio_btn = findViewById(R.id.volverInicio);
    }



    public boolean verificarNombreApellido() {
        String texto_nombre = nombre.getText().toString().trim();
        String texto_apellido = apellido.getText().toString().trim();
        boolean valido = true;
        String patron = "^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$";

        if (!texto_nombre.matches(patron)) {
            nombre.setError("Nombre inválido. Solo letras.");
            valido = false;
        }
        if (texto_nombre.length() < 3) {
            nombre.setError("El nombre debe tener al menos 3 caracteres.");
            valido = false;
        }

        if (!texto_apellido.matches(patron)) {
            apellido.setError("Apellido inválido. Solo letras.");
            valido = false;
        }
        if (texto_apellido.length() < 3) {
            apellido.setError("El apellido debe tener al menos 3 caracteres.");
            valido = false;
        }

        return valido;
    }

    public boolean verificarEdad(){
        String texto_edad=edad.getText().toString().trim();
        if (texto_edad.isEmpty()){
            edad.setError("La edad es obligatoria");
            return false;
        }else {
            try {
                int num_edad = Integer.parseInt(texto_edad);
                if (num_edad < 13) {
                    edad.setError("debes tener más de 13 añoas para registrarte");
                    return false;
                } else if (num_edad > 99) {
                    edad.setError("edad inválida");
                    return false;
                }else return true;
            } catch (NumberFormatException e) {
                edad.setError("Edad inválida.");
                return false;
            }
        }
    }


    public boolean verificarDni(){
        String texto_dniNum = dniNum.getText().toString().trim();
        if (texto_dniNum.isEmpty()){
            dniNum.setError("El DNI es obligatorio");
            return false;
        } else{
            try {
                if (texto_dniNum.length()<8) {
                    dniNum.setError("El DNI debe tener 8 dígitos");
                    return false;
                }if(!texto_dniNum.matches("\\d+")){
                    dniNum.setError("solo números");
                    return false;
                }else return true;
            }catch (NumberFormatException e){
                dniNum.setError("DNI inválido");
                return false;
            }
        }
    }


    public boolean verificarContra(EditText contra){
        String texto_contra = contra.getText().toString().trim();
        String patron = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return texto_contra.matches(patron);
    }

    public boolean compararContras(EditText contra, EditText confirContra) {
        String texto_contra = contra.getText().toString().trim();
        String texto_conf_contra = confirContra.getText().toString().trim();
        boolean valido = true;

        try {
            if (verificarContra(contra)) {
                contra.setError("La contraseña debe tener al menos 8 caracteres, una mayúscula y un número");
                valido = false;
            }
            if (verificarContra(confirContra)) {
                confirContra.setError("La contraseña debe tener al menos 8 caracteres, una mayúscula y un número");
                valido = false;
            }
            if (!texto_contra.equals(texto_conf_contra)) {
                contra.setError("Las contraseñas no coinciden.");
                confirContra.setError("Las contraseñas no coinciden.");
                valido = false;
            }
            if (texto_contra.isEmpty() || texto_conf_contra.isEmpty()){
                contra.setError("Ingrese una contraseña");
                confirContra.setError("Confirme la contraseña");
            }

        } catch (Exception e) {
            Toast.makeText(contra.getContext(), "Error al verificar las contraseñas", Toast.LENGTH_SHORT).show();
            valido = false;
        }
        return valido;
    }

    public void registrarse(View view){

        try{
            boolean nombreValido = verificarNombreApellido();
            boolean edadValida = verificarEdad();
            boolean dniValido = verificarDni();
            boolean contrasValidas = compararContras(contra,confirContra);

            if(!nombreValido || !edadValida || !dniValido || !contrasValidas){
                Toast.makeText(this, "Error al crear la cuenta, revise los campos", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"registro con exito",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this,"Error inesperado al crear la cuenta",Toast.LENGTH_SHORT).show();
        }

    }

    public void inicio(View view){
        try {
            inicio_btn.setTextColor(Color.parseColor("#E25683"));
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        }catch (NumberFormatException e){
            Toast.makeText(this,"No se pude volver al Login",Toast.LENGTH_SHORT).show();
        }

    }
    public void volverARegistro(View view){
        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }
}