package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.RecetaAdapter;
import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.dto.RecetaDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusquedaActivity extends AppCompatActivity {

    private EditText etSearchBar;
    private RecyclerView rvResultados;
    private RecetaAdapter adapter;
    private List<RecetaDTO> listaRecetas = new ArrayList<>();


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

        // Search bar y RecyclerView
        etSearchBar = findViewById(R.id.etSearchBar);
        rvResultados = findViewById(R.id.rvResultados);
        rvResultados.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecetaAdapter(listaRecetas, this);
        rvResultados.setAdapter(adapter);

        // Botón buscar por nombre
        findViewById(R.id.btnBuscarReceta).setOnClickListener(v -> buscarRecetaPorNombre());

        // Botón buscar usuario
        findViewById(R.id.btnBuscarUsuario).setOnClickListener(v -> buscarUsuario());
    }

    private void buscarRecetaPorNombre() {
        String texto = etSearchBar.getText().toString().trim();
        if (texto.isEmpty()) {
            Toast.makeText(this, "Ingresá un nombre para buscar.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.getInstance().getApiService().filtrarPorNombre(texto)
                .enqueue(new Callback<List<RecetaDTO>>() {
                    @Override
                    public void onResponse(Call<List<RecetaDTO>> call, Response<List<RecetaDTO>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listaRecetas.clear();
                            listaRecetas.addAll(response.body());
                            adapter.notifyDataSetChanged();
                            rvResultados.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(BusquedaActivity.this, "No se encontraron recetas.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RecetaDTO>> call, Throwable t) {
                        Toast.makeText(BusquedaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void buscarUsuario() {
        String texto = etSearchBar.getText().toString().trim();
        if (texto.isEmpty()) {
            Toast.makeText(this, "Ingresá un nombre para buscar.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.getInstance().getApiService().filtrarPorUsuario(texto)
                .enqueue(new Callback<List<RecetaDTO>>() {
                    @Override
                    public void onResponse(Call<List<RecetaDTO>> call, Response<List<RecetaDTO>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            listaRecetas.clear();
                            listaRecetas.addAll(response.body());
                            adapter.notifyDataSetChanged();
                            rvResultados.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(BusquedaActivity.this, "No se encontraron recetas.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RecetaDTO>> call, Throwable t) {
                        Toast.makeText(BusquedaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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


