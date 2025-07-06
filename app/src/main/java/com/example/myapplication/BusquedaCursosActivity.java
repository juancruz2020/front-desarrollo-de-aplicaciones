package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dto.SedeDTO;

public class BusquedaCursosActivity extends AppCompatActivity {
    TextView btnSedePalermo, btnSedeCaballito, btnSedeDevoto, btnSedeMicrocentro, btnSedeRetiro, btnSedeBarrioMitre, btnSedeNunez, btnSedeMonserrat;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_cursos);

        btnSedePalermo = findViewById(R.id.btnSedePalermo);
        btnSedeCaballito = findViewById(R.id.btnSedeCaballito);
        btnSedeDevoto = findViewById(R.id.btnSedeDevoto);
        btnSedeMicrocentro = findViewById(R.id.btnSedeMicrocentro);
        btnSedeRetiro = findViewById(R.id.btnSedeRetiro);
        btnSedeBarrioMitre = findViewById(R.id.btnSedeBarrioMitre);
        btnSedeNunez = findViewById(R.id.btnSedeNunez);
        btnSedeMonserrat = findViewById(R.id.btnSedeMonserrat);

        // Este botón flecha (back).
        // cierra esta pantalla y vuelve a la anterior.
        ImageButton btnBackSearch = findViewById(R.id.btnBackSearch);
        btnBackSearch.setOnClickListener(v -> {
            finish(); // Vuelve a la pantalla anterior sin abrir una nueva.
        });

        btnSedePalermo.setOnClickListener(v -> {
            SedeDTO sede = new SedeDTO(
                    "Sede Palermo",
                    "Nuestra moderna sede ubicada en el corazón de Palermo cuenta con las herramientas que necesitás para aprender.",
                    "Cabildo 123",
                    "4005-9234",
                    "Lunes - Sábados",
                    "07:00 hs - 22:00 hs,  Sábados: 8:00 hs - 13:00 hs",
                    R.drawable.sedepalermo
            );
            DetalleSede(sede);
        });

        btnSedeCaballito.setOnClickListener(v -> {
            SedeDTO sede = new SedeDTO(
                    "Sede Caballito",
                    "Nuestra moderna sede ubicada Caballito cuenta con las herramientas que necesitás para aprender.",
                    "Avenida Directorio 1662",
                    "4005-9234",
                    "Lunes - Jueves",
                    "07:00 hs - 20:00 hs",
                    R.drawable.sedecaballito
            );
            DetalleSede(sede);
        });

        btnSedeDevoto.setOnClickListener(v -> {
            SedeDTO sede = new SedeDTO(
                    "Sede Devoto",
                    "Nuestra moderna sede ubicada en Devoto cuenta con las herramientas que necesitás para aprender.",
                    "Belisario Roldan 4950",
                    "4774-5476",
                    "Lunes - Viernes",
                    "07:00 hs - 22:00 hs",
                    R.drawable.sededevoto
            );
            DetalleSede(sede);
        });

        btnSedeMicrocentro.setOnClickListener(v -> {
            SedeDTO sede = new SedeDTO(
                    "Sede Microcentro",
                    "Nuestra moderna sede ubicada en Microcentro cuenta con las herramientas que necesitás para aprender.",
                    "Esmeralda 141",
                    "4005-9234",
                    "Lunes - Viernes",
                    "07:00 hs - 20:00 hs",
                    R.drawable.sedemicrocentro
            );
            DetalleSede(sede);
        });

        btnSedeRetiro.setOnClickListener(v -> {
            SedeDTO sede = new SedeDTO(
                    "Sede Retiro",
                    "Nuestra moderna sede ubicada en Retiro cuenta con las herramientas que necesitás para aprender.",
                    "Cabildo 123",
                    "4005-9234",
                    "Lunes - Sábados",
                    "09:00 hs - 18:00 hs, Sábados: 8:00 hs - 13:00 hs",
                    R.drawable.sederetiro
            );
            DetalleSede(sede);
        });

        btnSedeBarrioMitre.setOnClickListener(v -> {
            SedeDTO sede = new SedeDTO(
                    "Sede Barrio Mitre",
                    "Nuestra moderna sede ubicada en Barrio Mitre cuenta con las herramientas que necesitás para aprender.",
                    "Correa 3930",
                    "4005-9234",
                    "Lunes - Viernes",
                    "09:00 hs - 20:00 hs",
                    R.drawable.sedebarriomitre
            );
            DetalleSede(sede);
        });

        btnSedeNunez.setOnClickListener(v -> {
            SedeDTO sede = new SedeDTO(
                    "Sede Nuñez",
                    "Nuestra moderna sede ubicada en Nuñez cuenta con las herramientas que necesitás para aprender.",
                    "Cabildo 123Av. Intendente Cantilo y Arroyo Medrano (1429)",
                    "4704-9272",
                    "Martes - Viernes",
                    "07:00 hs - 22:00 hs",
                    R.drawable.sedenunez
            );
            DetalleSede(sede);
        });

        btnSedeMonserrat.setOnClickListener(v -> {
            SedeDTO sede = new SedeDTO(
                    "Sede Monserrat",
                    "Nuestra moderna sede ubicada en Monserrat cuenta con las herramientas que necesitás para aprender.",
                    "Lima 757",
                    "4005-9234",
                    "Lunes - Sábados",
                    "07:00 hs - 22:00 hs, Sábados: 8:00 hs - 13:00 hs",
                    R.drawable.sedemonserrat
            );
            DetalleSede(sede);
        });

    }

    private void DetalleSede(SedeDTO sede) {
        Intent intent = new Intent(this, DetalleSedeActivity.class);
        intent.putExtra("sede", sede);
        startActivity(intent);
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
