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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    Receta receta;
    ImageView imgRecetaPortada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta_test);

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
        ImageView imgReceta = findViewById(R.id.imgReceta);
        TextView tvCantidadPorciones = findViewById(R.id.tvCantidadPorciones);
        imgRecetaPortada = findViewById(R.id.imgReceta);
        cargarUnidades();
        //Animacion de favoritos xd
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
        //boton para volver
        btnCerrar.setOnClickListener(v -> {
            Intent intent = new Intent(DetalleRecetaActivity.this, InicioActivity.class);
            intent.putExtra("receta", receta);
            startActivity(intent);
            finish();
        });

        //Logica de favoritos(animacion)
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
        //Botones para modificar el numero de porciones
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

        //Estrellas
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

        btnIngredientes.setOnClickListener(v -> {
            layoutIngredientes.setVisibility(View.VISIBLE);
            btnIngredientes.setBackgroundResource(R.drawable.tab_activo);
            btnIngredientes.setTextColor(Color.WHITE);
            btnInstrucciones.setBackgroundResource(R.drawable.tab_inactivo);
            btnInstrucciones.setTextColor(Color.parseColor("#0A2533"));
        });

        btnInstrucciones.setOnClickListener(v -> {
            layoutIngredientes.setVisibility(View.GONE);
            btnIngredientes.setBackgroundResource(R.drawable.tab_inactivo);
            btnIngredientes.setTextColor(Color.parseColor("#0A2533"));
            btnInstrucciones.setBackgroundResource(R.drawable.tab_activo);
            btnInstrucciones.setTextColor(Color.WHITE);
        });

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

        receta = (Receta) getIntent().getSerializableExtra("receta");

        if (receta == null) {
            Toast.makeText(this, "No se recibió la receta", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        txtTitulo.setText(receta.nombrePlato);
        tvCantidadPorciones.setText(String.valueOf(receta.cantidadPorciones));
        tvTiempoValor.setText(receta.tiempoValor);
        tvTiempoUnidad.setText(receta.tiempoUnidad);

        // Descripción
        TextView descripcion = findViewById(R.id.descripcion); // busca el id correcto
        descripcion.setText(receta.descripcion);

        // Ingredientes
        for (Ingrediente ing : receta.ingredientes) {
            View itemView = LayoutInflater.from(this).inflate(R.layout.item_ingrediente, layoutIngredientes, false);

            TextView tvNombre = itemView.findViewById(R.id.tvNombreIngrediente);
            TextView tvCantidad = itemView.findViewById(R.id.tvCantidadIngrediente);
            ImageButton btnMasIng = itemView.findViewById(R.id.btnMasCantidad);
            ImageButton btnMenosIng = itemView.findViewById(R.id.btnMenosCantidad);


            tvNombre.setText(ing.nombre + " (" + ing.unidad + ")");
            tvCantidad.setText(String.valueOf(ing.cantidad));

            // Obtengo el incremento según la unidad
            double incremento = incrementos.containsKey(ing.unidad) ? incrementos.get(ing.unidad) : 1.0;


            btnMasIng.setOnClickListener(v -> {
                ing.cantidad += incremento;
                tvCantidad.setText(String.format(Locale.getDefault(), "%.2f", ing.cantidad));
            });

            btnMenosIng.setOnClickListener(v -> {
                ing.cantidad = Math.max(0, ing.cantidad - incremento);
                tvCantidad.setText(String.format(Locale.getDefault(), "%.2f", ing.cantidad));
            });


            layoutIngredientes.addView(itemView);
        }


        // Pasos con foto
        for (int i = 0; i < receta.pasos.size(); i++) {
            Paso paso = receta.pasos.get(i);
            TextView tvPaso = new TextView(this);
            tvPaso.setText("Paso " + (i + 1) + ": " + paso.getDescripcion());
            layoutInstrucciones.addView(tvPaso);

            if (paso.getMediaPath() != null) {
                ImageView img = new ImageView(this);
                Glide.with(this)
                        .load(Uri.parse(paso.getMediaPath()))
                        .into(img);

                layoutInstrucciones.addView(img);
            }
            Log.d("Paso", "Media URI: " + paso.getMediaPath());

        }

        // Portada
        if (receta.portadaPath != null) {
            imgReceta.post(() -> imgReceta.setImageURI(Uri.parse(receta.portadaPath)));
        } else {
            imgReceta.setImageResource(R.drawable.ic_default);
        }



        // Recupera la receta
        receta = (Receta) getIntent().getSerializableExtra("receta");

        // Lógica para cargar la imagen de portada
        if (receta != null && receta.portadaPath != null) {
            try {
                Uri imageUri = Uri.parse(receta.portadaPath);
                imgRecetaPortada.setImageURI(imageUri);
            } catch (Exception e) {
                // Manejar cualquier error al parsear la URI o cargar la imagen
                e.printStackTrace();
                imgRecetaPortada.setImageResource(R.drawable.ic_default); // Usa una imagen por defecto en caso de error
                Toast.makeText(this, "Error al cargar la imagen de portada", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Si no hay portadaPath, muestra una imagen por defecto
            imgRecetaPortada.setImageResource(R.drawable.ic_default); // Asegúrate de tener un drawable llamado ic_default
        }


        star1.setTag(1); star2.setTag(2); star3.setTag(3); star4.setTag(4); star5.setTag(5);
        star1.setOnClickListener(starClickListener);
        star2.setOnClickListener(starClickListener);
        star3.setOnClickListener(starClickListener);
        star4.setOnClickListener(starClickListener);
        star5.setOnClickListener(starClickListener);


    }

    private void updateStars() {
        star1.setImageResource(rating >= 1 ? R.drawable.star_filled : R.drawable.star_empty);
        star2.setImageResource(rating >= 2 ? R.drawable.star_filled : R.drawable.star_empty);
        star3.setImageResource(rating >= 3 ? R.drawable.star_filled : R.drawable.star_empty);
        star4.setImageResource(rating >= 4 ? R.drawable.star_filled : R.drawable.star_empty);
        star5.setImageResource(rating >= 5 ? R.drawable.star_filled : R.drawable.star_empty);
    }

    //Valores por defecto de las unidades
    private void cargarUnidades() {
        valoresDefault.put("g", 50.0);      incrementos.put("g", 10.0);
        valoresDefault.put("kg", 0.5);      incrementos.put("kg", 0.1);
        valoresDefault.put("ml", 100.0);    incrementos.put("ml", 10.0);
        valoresDefault.put("u", 1.0);       incrementos.put("u", 1.0);
        valoresDefault.put("cda", 1.0);     incrementos.put("cda", 0.5);
        valoresDefault.put("cdita", 1.0);   incrementos.put("cdita", 0.25);
        valoresDefault.put("pizca", 1.0);   incrementos.put("pizca", 1.0);
        valoresDefault.put("taza", 1.0);    incrementos.put("taza", 0.25);
        valoresDefault.put("diente", 1.0);  incrementos.put("diente", 1.0);
        valoresDefault.put("hoja", 1.0);    incrementos.put("hoja", 1.0);
        valoresDefault.put("rama", 1.0);    incrementos.put("rama", 1.0);
    }



}
