package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.dto.IngredienteDTO;
import com.example.myapplication.dto.PasoDTO;
import com.example.myapplication.dto.RecetaDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleRecetaActivity extends AppCompatActivity {
    ImageButton btnCerrar;
    ImageView bookmarkEmpty, bookmarkFilled;
    FrameLayout bookmarkContainer;
    boolean isBookmarked = false;
    int porciones = 6;
    ImageView star1, star2, star3, star4, star5;
    int rating = 0;
    private final Map<String, Double> incrementos = new HashMap<>();
    private final Map<String, Double> valoresDefault = new HashMap<>();
    Receta receta; // Declaración de la variable para el objeto Receta local
    ImageView imgRecetaPortada;

    // Aquí declaramos el ID de la receta que vamos a buscar en la API
    private Long idRecetaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta_test);

        // Inicializa tus vistas aquí
        btnCerrar = findViewById(R.id.btnCerrar);
        bookmarkEmpty = findViewById(R.id.bookmarkEmpty);
        bookmarkFilled = findViewById(R.id.bookmarkFilled);
        bookmarkContainer = findViewById(R.id.bookmarkContainer);
        TextView tvPorciones = findViewById(R.id.tvCantidadPorciones);
        ImageButton btnMas = findViewById(R.id.btnMasPorciones);
        ImageButton btnMenos = findViewById(R.id.btnMenosPorciones);
        LinearLayout layoutIngredientes = findViewById(R.id.layoutIngredientes);
        TextView btnIngredientes = findViewById(R.id.btnIngredientes);
        TextView btnInstrucciones = findViewById(R.id.btnInstrucciones);
        LinearLayout layoutInstrucciones = findViewById(R.id.layoutInstrucciones);
        TextView txtTitulo = findViewById(R.id.txtTitulo);
        TextView tvTiempoValor = findViewById(R.id.tvTiempoValor);
        TextView tvTiempoUnidad = findViewById(R.id.tvTiempoUnidad);
        imgRecetaPortada = findViewById(R.id.imgReceta); // Este es tu ImageView principal de la receta.
        TextView tvCantidadPorciones = findViewById(R.id.tvCantidadPorciones);
        TextView descripcion = findViewById(R.id.descripcion); // Asegúrate de tener este ID en tu XML


        cargarUnidades(); // Carga los valores para unidades

        // **Paso 1: Obtener la receta preliminar o solo el ID del Intent**
        // Es crucial que el Intent traiga al menos el ID de la receta.
        Receta recetaDesdeIntent = (Receta) getIntent().getSerializableExtra("receta");

        if (recetaDesdeIntent == null || recetaDesdeIntent.idReceta == null) {
            Toast.makeText(this, "Error: No se recibió un ID de receta válido.", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si no hay un ID válido
            return;
        }

        // Asigna el ID para usarlo en la llamada a la API
        idRecetaActual = recetaDesdeIntent.idReceta;

        // **Paso 2: Realizar la llamada a la API para obtener el detalle completo**
        ApiClient.getInstance().getApiService().obtenerReceta(idRecetaActual).enqueue(new Callback<RecetaDTO>() {
            @Override
            public void onResponse(Call<RecetaDTO> call, Response<RecetaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RecetaDTO recetaDTO = response.body();
                    // Convierte el DTO a tu objeto Receta local para usarlo en la UI
                    receta = new Receta(recetaDTO); // Asegúrate de tener un constructor en Receta que reciba RecetaDTO

                    // **Paso 3: Ahora que tienes la receta completa, rellena la UI**
                    txtTitulo.setText(receta.nombrePlato);
                    tvCantidadPorciones.setText(String.valueOf(receta.cantidadPorciones));
                    tvTiempoValor.setText(receta.tiempoValor);
                    tvTiempoUnidad.setText(receta.tiempoUnidad);

                    if (descripcion != null) {
                        descripcion.setText(receta.descripcion);
                    }

                    // Cargar imagen de portada con Glide
                    if (receta.portadaPath != null && !receta.portadaPath.isEmpty()) {
                        Glide.with(DetalleRecetaActivity.this)
                                .load(receta.portadaPath)
                                .placeholder(R.drawable.ic_default)
                                .error(R.drawable.ic_default)
                                .into(imgRecetaPortada);
                    } else {
                        imgRecetaPortada.setImageResource(R.drawable.ic_default);
                    }

                    // Cargar Ingredientes
                    if (recetaDTO.getIngredientes() != null) {
                        layoutIngredientes.removeAllViews(); // Limpia antes de añadir
                        for (IngredienteDTO ing : recetaDTO.getIngredientes()) {
                            View itemView = LayoutInflater.from(DetalleRecetaActivity.this).inflate(R.layout.item_ingrediente, layoutIngredientes, false);
                            TextView tvNombre = itemView.findViewById(R.id.tvNombreIngrediente);
                            TextView tvCantidad = itemView.findViewById(R.id.tvCantidadIngrediente);
                            ImageButton btnMasIng = itemView.findViewById(R.id.btnMasCantidad);
                            ImageButton btnMenosIng = itemView.findViewById(R.id.btnMenosCantidad);

                            tvNombre.setText(ing.getNombre() + " (" + ing.getUnidad() + ")");
                            tvCantidad.setText(String.valueOf(ing.getCantidad()));

                            double incremento = incrementos.containsKey(ing.getUnidad()) ? incrementos.get(ing.getUnidad()) : 1.0;

                            btnMasIng.setOnClickListener(v -> {
                                ing.setCantidad((int) (ing.getCantidad() + incremento));
                                tvCantidad.setText(String.format(Locale.getDefault(), "%.2f", ing.getCantidad()));
                            });

                            btnMenosIng.setOnClickListener(v -> {
                                ing.setCantidad((int) Math.max(0, ing.getCantidad() - incremento));
                                tvCantidad.setText(String.format(Locale.getDefault(), "%.2f", ing.getCantidad()));
                            });
                            layoutIngredientes.addView(itemView);
                        }
                    }

                    // Cargar Pasos
                    if (recetaDTO.getPasos() != null) {
                        layoutInstrucciones.removeAllViews(); // Limpia antes de añadir
                        for (int i = 0; i < recetaDTO.getPasos().size(); i++) {
                            PasoDTO paso = recetaDTO.getPasos().get(i);
                            LinearLayout pasoContainer = new LinearLayout(DetalleRecetaActivity.this);
                            pasoContainer.setOrientation(LinearLayout.VERTICAL);
                            pasoContainer.setPadding(0, 16, 0, 16);

                            TextView tvPaso = new TextView(DetalleRecetaActivity.this);
                            tvPaso.setText("Paso " + paso.getNroPaso() + ": " + paso.getTexto());
                            tvPaso.setTextSize(16);
                            tvPaso.setTextColor(Color.BLACK);
                            pasoContainer.addView(tvPaso);

                            if (paso.getUrl() != null && !paso.getUrl().isEmpty()) {
                                ImageView img = new ImageView(DetalleRecetaActivity.this);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        (int) getResources().getDimension(R.dimen.instruction_image_height)
                                );
                                params.topMargin = 16;
                                img.setLayoutParams(params);
                                img.setScaleType(ImageView.ScaleType.CENTER_CROP);

                                Glide.with(DetalleRecetaActivity.this)
                                        .load(Uri.parse(paso.getUrl()))
                                        .placeholder(R.drawable.ic_default)
                                        .error(R.drawable.ic_default)
                                        .into(img);
                                pasoContainer.addView(img);
                            }
                            layoutInstrucciones.addView(pasoContainer);
                        }
                    }

                } else {
                    Toast.makeText(DetalleRecetaActivity.this, "Error al obtener detalles de la receta: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Respuesta no exitosa: " + response.message() + " Código: " + response.code());
                    finish(); // Cierra la actividad si no se puede obtener el detalle
                }
            }

            @Override
            public void onFailure(Call<RecetaDTO> call, Throwable t) {
                Log.e("API_ERROR", "Fallo de conexión al obtener receta: " + t.getMessage(), t);
                Toast.makeText(DetalleRecetaActivity.this, "Fallo conexión al detalle: " + t.getMessage(), Toast.LENGTH_LONG).show();
                finish(); // Cierra la actividad en caso de fallo de conexión
            }
        });


        // Animación de favoritos
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_scale);

        // Botón para volver
        btnCerrar.setOnClickListener(v -> {
            Intent intent = new Intent(DetalleRecetaActivity.this, InicioActivity.class);
            // No es necesario pasar la receta de vuelta a InicioActivity aquí a menos que InicioActivity lo necesite para una acción específica.
            startActivity(intent);
            finish();
        });


        // Lógica de favoritos (animación)
        bookmarkContainer.setOnClickListener(v -> {
            isBookmarked = !isBookmarked;
            if (isBookmarked) {
                bookmarkFilled.setVisibility(View.VISIBLE);
                bookmarkFilled.startAnimation(anim);
                bookmarkEmpty.setVisibility(View.GONE);
            } else {
                bookmarkEmpty.setVisibility(View.VISIBLE);
                bookmarkEmpty.startAnimation(anim);
                bookmarkFilled.setVisibility(View.GONE);
            }
        });

        // Botones para modificar el número de porciones
        btnMas.setOnClickListener(v -> {
            porciones++;
            tvPorciones.setText(String.valueOf(porciones));
        });

        btnMenos.setOnClickListener(v -> {
            if (porciones > 1) {
                porciones--;
                tvPorciones.setText(String.valueOf(porciones));
            }
        });

        // Estrellas
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        View.OnClickListener starClickListener = v -> {
            int clickedStar = Integer.parseInt(v.getTag().toString());
            if (rating == clickedStar) {
                rating = 0; // Deseleccionar si ya estaba seleccionada
            } else {
                rating = clickedStar;
            }
            updateStars();
        };

        star1.setTag(1);
        star2.setTag(2);
        star3.setTag(3);
        star4.setTag(4);
        star5.setTag(5);
        star1.setOnClickListener(starClickListener);
        star2.setOnClickListener(starClickListener);
        star3.setOnClickListener(starClickListener);
        star4.setOnClickListener(starClickListener);
        star5.setOnClickListener(starClickListener);

        // Lógica para los tabs de Ingredientes/Instrucciones
        btnIngredientes.setOnClickListener(v -> {
            layoutIngredientes.setVisibility(View.VISIBLE);
            layoutInstrucciones.setVisibility(View.GONE);
            btnIngredientes.setBackgroundResource(R.drawable.tab_activo);
            btnIngredientes.setTextColor(Color.WHITE);
            btnInstrucciones.setBackgroundResource(R.drawable.tab_inactivo);
            btnInstrucciones.setTextColor(Color.parseColor("#0A2533"));
        });

        btnInstrucciones.setOnClickListener(v -> {
            layoutIngredientes.setVisibility(View.GONE);
            layoutInstrucciones.setVisibility(View.VISIBLE);
            btnIngredientes.setBackgroundResource(R.drawable.tab_inactivo);
            btnIngredientes.setTextColor(Color.parseColor("#0A2533"));
            btnInstrucciones.setBackgroundResource(R.drawable.tab_activo);
            btnInstrucciones.setTextColor(Color.WHITE);
        });

        // Inicializa el primer tab como activo (Ingredientes)
        layoutIngredientes.setVisibility(View.VISIBLE);
        layoutInstrucciones.setVisibility(View.GONE);
        btnIngredientes.setBackgroundResource(R.drawable.tab_activo);
        btnIngredientes.setTextColor(Color.WHITE);
        btnInstrucciones.setBackgroundResource(R.drawable.tab_inactivo);
        btnInstrucciones.setTextColor(Color.parseColor("#0A2533"));
    }

    private void updateStars() {
        star1.setImageResource(rating >= 1 ? R.drawable.star_filled : R.drawable.star_empty);
        star2.setImageResource(rating >= 2 ? R.drawable.star_filled : R.drawable.star_empty);
        star3.setImageResource(rating >= 3 ? R.drawable.star_filled : R.drawable.star_empty);
        star4.setImageResource(rating >= 4 ? R.drawable.star_filled : R.drawable.star_empty);
        star5.setImageResource(rating >= 5 ? R.drawable.star_filled : R.drawable.star_empty);
    }

    private void cargarUnidades() {
        valoresDefault.put("g", 50.0);
        incrementos.put("g", 10.0);
        valoresDefault.put("kg", 0.5);
        incrementos.put("kg", 0.1);
        valoresDefault.put("ml", 100.0);
        incrementos.put("ml", 10.0);
        valoresDefault.put("u", 1.0);
        incrementos.put("u", 1.0);
        valoresDefault.put("cda", 1.0);
        incrementos.put("cda", 0.5);
        valoresDefault.put("cdita", 1.0);
        incrementos.put("cdita", 0.25);
        valoresDefault.put("pizca", 1.0);
        incrementos.put("pizca", 1.0);
        valoresDefault.put("taza", 1.0);
        incrementos.put("taza", 0.25);
        valoresDefault.put("diente", 1.0);
        incrementos.put("diente", 1.0);
        valoresDefault.put("hoja", 1.0);
        incrementos.put("hoja", 1.0);
        valoresDefault.put("rama", 1.0);
        incrementos.put("rama", 1.0);
    }
}
