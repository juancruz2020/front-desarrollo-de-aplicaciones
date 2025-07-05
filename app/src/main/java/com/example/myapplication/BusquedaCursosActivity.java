package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BusquedaCursosActivity extends AppCompatActivity {
    TextView btnSedePalermo, btnSedeCaballito, btnSedeDevoto, btnSedeMicrocentro, btnSedeRetiro, btnSedeBarrioMitre, btnSedeNunez, btnSedeMonserrat;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_cursos);

        btnSedePalermo=findViewById(R.id.btnSedePalermo);
        btnSedeCaballito=findViewById(R.id.btnSedeCaballito);
        btnSedeDevoto= findViewById(R.id.btnSedeDevoto);
        btnSedeMicrocentro=findViewById(R.id.btnSedeMicrocentro);
        btnSedeRetiro=findViewById(R.id.btnSedeRetiro);
        btnSedeBarrioMitre=findViewById(R.id.btnSedeBarrioMitre);
        btnSedeNunez=findViewById(R.id.btnSedeNunez);
        btnSedeMonserrat=findViewById(R.id.btnSedeMonserrat);

        // Este botón flecha (back).
        // cierra esta pantalla y vuelve a la anterior.
        ImageButton btnBackSearch = findViewById(R.id.btnBackSearch);
        btnBackSearch.setOnClickListener(v -> {
            finish(); // Vuelve a la pantalla anterior sin abrir una nueva.
        });

        btnSedePalermo.setOnClickListener(v ->{
            Intent intent = new Intent(this, DetalleSedeActivity.class);
            startActivity(intent);
        });

    }


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
        Intent intent= new Intent(this, BusquedaActivity.class);
        startActivity(intent);
    }
}
