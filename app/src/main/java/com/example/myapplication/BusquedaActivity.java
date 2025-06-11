package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class BusquedaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        // Este botón está en el footer.
        // esto vuelve a la pantalla de inicio.
        LinearLayout btnFooterInicio = findViewById(R.id.btnFooterInicio);
        btnFooterInicio.setOnClickListener(v -> {
            Intent intent = new Intent(BusquedaActivity.this, InicioActivity.class);
            startActivity(intent);
        });

        // Este botón está en el footer.
        // lleva a la pantalla para cargar una receta.
        FrameLayout btnCargarReceta = findViewById(R.id.btnCargarReceta);
        btnCargarReceta.setOnClickListener(v -> {
            Intent intent = new Intent(BusquedaActivity.this, CargarRecetaActivity.class);
            startActivity(intent);
        });

        // Este botón flecha (back).
        // cierra esta pantalla y vuelve a la anterior.
        ImageButton btnBackSearch = findViewById(R.id.btnBackSearch);
        btnBackSearch.setOnClickListener(v -> {
            finish(); // Vuelve a la pantalla anterior sin abrir una nueva.
        });
    }
}


