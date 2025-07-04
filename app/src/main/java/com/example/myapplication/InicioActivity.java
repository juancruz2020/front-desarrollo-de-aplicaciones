/*
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

import com.bumptech.glide.Glide;
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

//  GET DE RECETAS PARA MOSTRARLAS - FALTA CONSUMIR LAS IMAGENES!

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
        // ir a cursos
        btnCursos.setOnClickListener(v -> {
            Intent intent = new Intent(this, CursosActivity.class);
            startActivity(intent);
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

        if (receta.portadaPath != null && !receta.portadaPath.isEmpty()) {
            Glide.with(this)
                    .load(receta.portadaPath)           // <-- AQUÍ VA LA URL DE CLOUDINARY
                    .placeholder(R.drawable.ic_default) // Mientras carga
                    .error(R.drawable.ic_default)       // Si falla
                    .into(imgReceta);
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



}
*/

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

import com.bumptech.glide.Glide;
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
    LinearLayout contenedorDescubrir; // Cambiado a LinearLayout para que coincida con el ID del layout
    FlexboxLayout contenedorFavoritas; // Ahora es FlexboxLayout como en el layout
    View lineaSelector;
    LinearLayout tabOptions;
    private ViewPager2 promoSlider;
    private Handler sliderHandler = new Handler();
    private List<Integer> imagenesPromo;

    // Agregamos una referencia al contenedor donde se mostrarán las recetas principales
    private LinearLayout contenedorUltimasRecetas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // Inicializa tus vistas aquí, después de setContentView
        tabDescubrir = findViewById(R.id.tabDescubrir);
        tabFavoritas = findViewById(R.id.tabFavoritas);
        contenedorDescubrir = findViewById(R.id.contenedorDescubrir);
        contenedorFavoritas = findViewById(R.id.contenedorFavoritas); // Asegúrate de que este ID sea correcto en tu layout si es FlexboxLayout
        lineaSelector = findViewById(R.id.lineaSelector);
        tabOptions = findViewById(R.id.tabOptions);
        FrameLayout btnCargarReceta = findViewById(R.id.btnCargarReceta);
        ImageView btnBuscarHeader = findViewById(R.id.btnBuscar);
        LinearLayout btnBuscarFooter = findViewById(R.id.btnFooterBuscar);
        LinearLayout btnPerfil = findViewById(R.id.btnFooterPerfil);
        promoSlider = findViewById(R.id.promoSlider);
        // Inicializa el contenedor de las últimas recetas
        contenedorUltimasRecetas = findViewById(R.id.contenedorUltimas); // Asume que este es el ID del LinearLayout donde quieres mostrar las recetas principales

        /* GET DE RECETAS PARA MOSTRARLAS
         * La lógica ya está aquí, solo se asegura de que se agreguen al contenedor correcto
         */
        ApiClient.getInstance().getApiService().listarTodas().enqueue(new Callback<List<RecetaDTO>>() {
            @Override
            public void onResponse(Call<List<RecetaDTO>> call, Response<List<RecetaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Limpiamos el contenedor antes de agregar nuevas recetas para evitar duplicados si la actividad se recrea
                    contenedorUltimasRecetas.removeAllViews();
                    for (RecetaDTO recetaDTO : response.body()) {
                        Receta receta = new Receta(recetaDTO); // Asegúrate de que el constructor de Receta que toma un RecetaDTO sea correcto
                        agregarRecetaAlInicio(receta); // Usa el método para agregar cada receta
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
        // Puedes eliminar o comentar esta sección si ya no la necesitas, ya que las recetas se cargarán dinámicamente.
        LinearLayout itemRecetaNoquis = findViewById(R.id.itemRecetaNoquis);
        if (itemRecetaNoquis != null) { // Asegúrate de que exista en tu layout
            itemRecetaNoquis.setOnClickListener(v -> {
                // Aquí deberías pasar el objeto Receta correspondiente a los ñoquis si quieres que funcione de forma similar
                // Por ahora, solo lleva a la actividad sin datos específicos
                Intent intent = new Intent(InicioActivity.this, DetalleRecetaActivity.class);
                startActivity(intent);
            });
        }


        //Esto te manda a la pantalla para cargar receta
        btnCargarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this, CargarRecetaActivity.class);
                startActivity(intent);
            }
        });

        //Agrega la receta creada al inicio (si se viene de CargarRecetaActivity).
        Receta recetaDesdeCarga = (Receta) getIntent().getSerializableExtra("receta");
        if (recetaDesdeCarga != null) {
            agregarRecetaAlInicio(recetaDesdeCarga);
        }

        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, PerfilActivity.class);
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

    /**
     * Función para mostrar la tarjeta de una receta en el contenedor de "Últimas Recetas" en el inicio.
     * @param receta El objeto Receta con los datos a mostrar.
     */
    private void agregarRecetaAlInicio(Receta receta) {
        // Asegúrate de que contenedorUltimasRecetas ya esté inicializado en onCreate
        if (contenedorUltimasRecetas == null) {
            Log.e("InicioActivity", "contenedorUltimasRecetas no inicializado.");
            return;
        }

        View item = LayoutInflater.from(this).inflate(R.layout.item_card_receta, contenedorUltimasRecetas, false);

        TextView tvTitulo = item.findViewById(R.id.tvTituloCard);
        TextView tvDescripcion = item.findViewById(R.id.tvDescripcionCard);
        ImageView imgReceta = item.findViewById(R.id.imgRecetaCard);

        tvTitulo.setText(receta.nombrePlato);
        tvDescripcion.setText(receta.descripcion);

        // Carga la imagen usando Glide
        if (receta.portadaPath != null && !receta.portadaPath.isEmpty()) {
            Glide.with(this)
                    .load(receta.portadaPath)           // <-- AQUÍ VA LA URL DE CLOUDINARY
                    .placeholder(R.drawable.ic_default) // Imagen de placeholder mientras carga
                    .error(R.drawable.ic_default)       // Imagen si ocurre un error al cargar
                    .into(imgReceta);
        } else {
            imgReceta.setImageResource(R.drawable.ic_default); // Imagen por defecto si no hay URL
        }

        // Configura el OnClickListener para navegar a DetalleRecetaActivity
        item.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleRecetaActivity.class);
            intent.putExtra("receta", receta); // Envía el objeto Receta completo
            startActivity(intent);
        });

        contenedorUltimasRecetas.addView(item, 0); // Agrega la receta al principio de la lista
    }
}