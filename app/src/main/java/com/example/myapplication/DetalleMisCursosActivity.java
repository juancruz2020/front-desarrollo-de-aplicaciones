package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;



public class DetalleMisCursosActivity extends AppCompatActivity {

    CheckBox checkboxTarjeta, checkboxSaldoEnCuenta; ImageButton btnCerrar; LinearLayout contenedorMisCursos;

    private static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_miscursos);

        checkboxTarjeta = findViewById(R.id.checkboxTarjeta);
        checkboxSaldoEnCuenta = findViewById(R.id.checkboxSaldoEnCuenta);
        btnCerrar = findViewById(R.id.btnCerrar);

        checkboxTarjeta.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxSaldoEnCuenta.setChecked(false);
            }
        });

        checkboxSaldoEnCuenta.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxTarjeta.setChecked(false);
            }
        });

        ImageButton qrButton = findViewById(R.id.QR);
        qrButton.setOnClickListener(v -> abrirCamara());

    }

    private void cerrar(View view){
        Intent intent = new Intent(this, CursosActivity.class);
        startActivity(intent);
    }
    private void abrirCamara() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
