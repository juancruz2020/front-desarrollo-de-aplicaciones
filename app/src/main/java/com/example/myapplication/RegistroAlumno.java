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


public class RegistroAlumno extends AppCompatActivity {

    ImageButton btnVolver; Button inicio_btn, crearCuenta_btn; EditText nro_tarjeta, nro_tramite,cCorriente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_registro_alumno);

        btnVolver = findViewById(R.id.btn_volver);
        inicio_btn = findViewById(R.id.volverInicio);
        nro_tarjeta = findViewById(R.id.nroTarjeta);
        nro_tramite = findViewById(R.id.nTramite);
        cCorriente = findViewById(R.id.corriente);
        crearCuenta_btn = findViewById(R.id.btnCrearCuenta);

    }


    public boolean validarNumeroTramite(String numeroTramite) {
        return numeroTramite.matches("\\d{11}"); // Verifica que tenga exactamente 11 dígitos numéricos
    }

    public boolean verificarNroTramite(EditText nro_tramite){
        String tramite = nro_tramite.getText().toString().trim();
        if (!validarNumeroTramite(tramite)) {
            nro_tramite.setError("El número debe tener 11 digitos");
            return false;
        } else{
            return true;
        }
    }

    public boolean validarNumeroTarjeta(String numeroTarjeta) {

        int suma = 0;
        boolean alternar = false;

        for (int i = numeroTarjeta.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numeroTarjeta.charAt(i));

            if (alternar) {
                digito *= 2;
                if (digito > 9) digito -= 9;
            }

            suma += digito;
            alternar = !alternar;
        }

        return suma % 10 == 0; // Si el resultado es múltiplo de 10, la tarjeta es válida
    }
    public boolean verificarTarjeta(EditText nro_tarjeta){
        String tarjeta = nro_tarjeta.getText().toString().trim();
        boolean valido = true;
        if (!validarNumeroTarjeta(tarjeta)) {
            nro_tarjeta.setError("ERROR! Por favor, ingrese un número válido");
            valido = false;
        }if(tarjeta.isEmpty()){
            nro_tarjeta.setError("El campo no puede estar vacio");
            valido = false;
        }if(tarjeta.length()!=9){
            nro_tarjeta.setError("El número debe tener 9 digitos");
            valido = false;
        }

        return  valido;
    }

    public boolean validarCuentaCorriente(String cuentaCorriente) {
        return cuentaCorriente.matches("\\d{13}"); // Asegura que tenga exactamente 13 dígitos
    }
    public boolean verificarCuenta(EditText cCorriente) {
        String cuenta = cCorriente.getText().toString().trim();
        boolean valido = true;

        if(cuenta.isEmpty()) {
            cCorriente.setError("El campo es obligatorio");
            valido = false;
        }
        else if (!validarCuentaCorriente(cuenta)) {
            cCorriente.setError("El número de cuenta corriente debe tener 13 dígitos");
            valido = false;
        }
        return valido;
    }
    

    // Hacer DNI_Foto (Frente y Dorso) max 20mb, multipartFile

    public void volverARegistro(View view){
        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }

    public void inicio(View view){
        inicio_btn.setTextColor(Color.parseColor("#E25683"));
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public void registrarAlumno(View view){
        try {
            boolean tarjetaValida = verificarTarjeta(nro_tarjeta);
            boolean tramiteValido = verificarNroTramite(nro_tramite);
            boolean cuentaValida = verificarCuenta(cCorriente);
            //verificarDNI

            if (!tarjetaValida||!tramiteValido||!cuentaValida){
                Toast.makeText(this,"Error al crear la cuenta, revise los campos",Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this,"registro con exito",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this,"Error al crear la cuenta, revise los campos",Toast.LENGTH_SHORT).show();
        }

    }


}