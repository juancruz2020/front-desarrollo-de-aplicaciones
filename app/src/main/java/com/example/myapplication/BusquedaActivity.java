package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    //navegacion Footer

    public void irInicio(View view){
        Intent intent= new Intent(this, InicioActivity.class);
        startActivity(intent);
    }
    public void irPerfil(View view){
        Intent intent= new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }

    public void cargarReceta(View view){
        Intent intent= new Intent(this, CargarRecetaActivity.class);
        startActivity(intent);
    }

    public void irCursos(View view){
        Intent intent= new Intent(this, CursosActivity.class);
        startActivity(intent);
    }

    public void irBusqueda(View view){
        Intent intent= new Intent(this, BusquedaCursosActivity.class);
        startActivity(intent);
    }
}


