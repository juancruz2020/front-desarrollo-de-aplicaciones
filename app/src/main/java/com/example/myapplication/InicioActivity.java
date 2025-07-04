package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.dto.RecetaDTO;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioActivity extends AppCompatActivity {

    TextView tabDescubrir, tabFavoritas;
    LinearLayout contenedorDescubrir, contenedorFavoritas;
    View lineaSelector;
    LinearLayout tabOptions;
    private ViewPager2 promoSlider;
    private Handler sliderHandler = new Handler();
    private List<Integer> imagenesPromo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

/*  GET DE RECETAS PARA MOSTRARLAS - FALTA CONSUMIR LAS IMAGENES!

       ApiClient.getInstance().getApiService().listarTodas().enqueue(new Callback<List<RecetaDTO>>() {
            @Override
            public void onResponse(Call<List<RecetaDTO>> call, Response<List<RecetaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (RecetaDTO recetaDTO : response.body()) {
                        Receta receta = new Receta(recetaDTO); // Asegurate de tener este constructor
                        agregarRecetaAlInicio(receta);
                    }
                } else {
                    Toast.makeText(InicioActivity.this, "Error al obtener recetas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RecetaDTO>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage(), t);
                Toast.makeText(InicioActivity.this, "Fallo conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        tabDescubrir = findViewById(R.id.tabDescubrir);
        tabFavoritas = findViewById(R.id.tabFavoritas);
        contenedorDescubrir = findViewById(R.id.contenedorDescubrir);
        FlexboxLayout contenedorFavoritas = findViewById(R.id.contenedorFavoritas);
        lineaSelector = findViewById(R.id.lineaSelector);
        tabOptions = findViewById(R.id.tabOptions);
        FrameLayout btnCargarReceta = findViewById(R.id.btnCargarReceta);
        ImageView btnBuscarHeader = findViewById(R.id.btnBuscar);
        LinearLayout btnBuscarFooter = findViewById(R.id.btnFooterBuscar);
        LinearLayout btnPerfil = findViewById(R.id.btnFooterPerfil);
        LinearLayout btnCursos = findViewById(R.id.btnCursos);

        tabOptions.post(() -> {
            int anchoMitad = tabOptions.getWidth() / 2;
            LayoutParams params = lineaSelector.getLayoutParams();
            params.width = anchoMitad;
            lineaSelector.setLayoutParams(params);
            lineaSelector.setTranslationX(0);
        }); // Esto maneja la linea del tab activo

        tabDescubrir.setOnClickListener(v -> {
            contenedorDescubrir.setVisibility(View.VISIBLE);
            contenedorFavoritas.setVisibility(View.GONE);
            tabDescubrir.setTextColor(Color.parseColor("#FFE169"));
            tabFavoritas.setTextColor(Color.parseColor("#FFFFFF"));
            lineaSelector.animate().translationX(0).setDuration(300).start();
        }); //Muestra el tab de Descubrir

        tabFavoritas.setOnClickListener(v -> {
            contenedorDescubrir.setVisibility(View.GONE);
            contenedorFavoritas.setVisibility(View.VISIBLE);
            tabFavoritas.setTextColor(Color.parseColor("#FFE169"));
            tabDescubrir.setTextColor(Color.parseColor("#FFFFFF"));
            int anchoMitad = tabOptions.getWidth() / 2;
            lineaSelector.animate().translationX(anchoMitad).setDuration(300).start();
        }); //Muestra el tab de Favoritas

        View.OnClickListener irABusqueda = v -> {
            Intent intent = new Intent(InicioActivity.this, BusquedaActivity.class);
            startActivity(intent);
        };
        btnBuscarHeader.setOnClickListener(irABusqueda);
        btnBuscarFooter.setOnClickListener(irABusqueda);
        //Botones de busqueda.

        contenedorDescubrir.setVisibility(View.VISIBLE); //Empieza Descubrir visible
        contenedorFavoritas.setVisibility(View.GONE); //Empieza Favorita invisible.
        tabDescubrir.setTextColor(Color.parseColor("#FFE169"));
        tabFavoritas.setTextColor(Color.parseColor("#FFFFFF"));

        //Todo esto es del slider
        promoSlider = findViewById(R.id.promoSlider);

        imagenesPromo = new ArrayList<>();
        imagenesPromo.add(R.drawable.fotopromocionaliniciocursos);
        imagenesPromo.add(R.drawable.imagenpromocionalinicio1);
        imagenesPromo.add(R.drawable.imagenpromocionalinicio2);

        PromoAdapter adapter = new PromoAdapter(this, imagenesPromo);
        promoSlider.setAdapter(adapter);

        promoSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 5000);
            }
        }); //Esto es el temporizador para que avance solo

        //Esto esta HARDCODEADO, es para abrir la receta de ñoquis(BORRAR MAS ADELANTE)
        LinearLayout itemRecetaNoquis = findViewById(R.id.itemRecetaNoquis);
        itemRecetaNoquis.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, DetalleRecetaActivity.class);
            startActivity(intent);
        });

        //Esto te manda a la pantalla para cargar receta
        btnCargarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, CargarRecetaActivity.class);
                startActivity(intent);
            }
        });

        //Agrega la receta creada al inicio.
        Receta receta = (Receta) getIntent().getSerializableExtra("receta");
        if (receta != null) {
            agregarRecetaAlInicio(receta);
        }

        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, PerfilActivity.class);
            startActivity(intent);
        });

        btnCursos.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, CursosActivity.class);
            startActivity(intent);
        });
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int nextItem = (promoSlider.getCurrentItem() + 1) % imagenesPromo.size();
            promoSlider.setCurrentItem(nextItem, true);
        }
    };

    //Funcion para mostrar la tarjeta de la receta en el inicio.
    private void agregarRecetaAlInicio(Receta receta) {
        LinearLayout contenedor = findViewById(R.id.contenedorUltimas);

        View item = LayoutInflater.from(this).inflate(R.layout.item_card_receta, contenedor, false);

        TextView tvTitulo = item.findViewById(R.id.tvTituloCard);
        TextView tvDescripcion = item.findViewById(R.id.tvDescripcionCard);
        ImageView imgReceta = item.findViewById(R.id.imgRecetaCard);

        tvTitulo.setText(receta.nombrePlato);
        tvDescripcion.setText(receta.descripcion);

        if (receta.portadaPath != null) {
            imgReceta.setImageURI(Uri.parse(receta.portadaPath));
        } else {
            imgReceta.setImageResource(R.drawable.ic_default);
        }

        item.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleRecetaActivity.class);
            intent.putExtra("receta", receta);
            startActivity(intent);
        });

        contenedor.addView(item, 0); // al principio de la lista
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
