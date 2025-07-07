package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.conection.ApiService;
import com.example.myapplication.dto.CronogramaCursoPlanoDTO;
import com.example.myapplication.dto.CursoDTO;
import com.example.myapplication.dto.curso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CursosActivity extends AppCompatActivity {

    TextView tabDescubrir, tabMisCursos;
    LinearLayout contenedorDescubrir, contenedorMisCursos;
    View lineaSelector;
    LinearLayout tabOptions;

    List<curso> cursos = new ArrayList<>();  // Lista de cursos

    List<CronogramaCursoPlanoDTO> cursosmios = new ArrayList<>();  // Lista de cursos

    LinearLayout contenedorEnCurso, contenedorFinalizados;
    LinearLayout bloqueo;

    String tipoUsuario="alumno";

    int idcurso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        // Vinculamos views
        tabDescubrir = findViewById(R.id.tabDescubrir);
        tabMisCursos = findViewById(R.id.tabMisCursos);
        contenedorDescubrir = findViewById(R.id.contenedorCursosDescubrir);
        contenedorMisCursos = findViewById(R.id.contenedorMisCursos);
        lineaSelector = findViewById(R.id.lineaSelector);
        tabOptions = findViewById(R.id.tabOptions);
        contenedorEnCurso = findViewById(R.id.ContenedorEnCurso);
        contenedorFinalizados = findViewById(R.id.ContenedorFinalizados);
        ImageView btnBuscarHeader = findViewById(R.id.btnBuscar);
        LinearLayout btnBuscarFooter = findViewById(R.id.btnFooterBuscar);

        // Ajustamos ancho de lineaSelector según la mitad del ancho del tabOptions
        tabOptions.post(() -> {
            int anchoMitad = tabOptions.getWidth() / 2;
            LayoutParams params = lineaSelector.getLayoutParams();
            params.width = anchoMitad;
            lineaSelector.setLayoutParams(params);
            lineaSelector.setTranslationX(0);
        });

        // Configuramos comportamiento de los tabs
        tabDescubrir.setOnClickListener(v -> {
            contenedorDescubrir.setVisibility(View.VISIBLE);
            contenedorMisCursos.setVisibility(View.GONE);
            tabDescubrir.setTextColor(Color.parseColor("#FFBA61"));
            tabMisCursos.setTextColor(Color.parseColor("#FFFFFF"));
            lineaSelector.animate().translationX(0).setDuration(300).start();
        });

        tabMisCursos.setOnClickListener(v -> {
            contenedorDescubrir.setVisibility(View.GONE);
            contenedorMisCursos.setVisibility(View.VISIBLE);
            tabDescubrir.setTextColor(Color.parseColor("#FFFFFF"));
            tabMisCursos.setTextColor(Color.parseColor("#FFBA61"));
            int anchoMitad = tabOptions.getWidth() / 2;
            lineaSelector.animate().translationX(anchoMitad).setDuration(300).start();
        });

        // Inicializamos con Descubrir visible
        contenedorDescubrir.setVisibility(View.VISIBLE);
        contenedorMisCursos.setVisibility(View.GONE);
        tabDescubrir.setTextColor(Color.parseColor("#FFBA61"));
        tabMisCursos.setTextColor(Color.parseColor("#FFFFFF"));

        cargarCursosDescubrir();
        cargarMisCursos();

        //buscar cursos
        View.OnClickListener irABusqueda = v -> {
            Intent intent = new Intent(CursosActivity.this, BusquedaCursosActivity.class);
            startActivity(intent);
        };
        btnBuscarHeader.setOnClickListener(irABusqueda);
        btnBuscarFooter.setOnClickListener(irABusqueda);

        bloqueo= findViewById(R.id.bloqueoVisitante);

        if (!"alumno".equals(tipoUsuario)) {
            bloqueo.setVisibility(View.VISIBLE);
        } else {
            bloqueo.setVisibility(View.GONE);
        }

    }

    //navegacion
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

    private void cargarCursosDescubrir() {
        cursos.clear(); // limpiamos los cursos actuales

        CursoDTO body = new CursoDTO(); // si hace falta setear el mail, acá lo hacés

        body.setMail("juancruzm2004@gmail.com");
        ApiService apiService = ApiClient.getInstance().getApiService();

        Call<List<curso>> call = apiService.obtenerCursosRegistrados(body);

        call.enqueue(new Callback<List<curso>>() {
            @Override
            public void onResponse(Call<List<curso>> call, Response<List<curso>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (curso curso : response.body()) {

                        cursos.add(curso);
                        agregarCursoAlBody(curso);
                    }
                } else {
                    Toast.makeText(CursosActivity.this, "Error al obtener cursos", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<curso>> call, Throwable t) {
                Toast.makeText(CursosActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void cargarMisCursos() {
        cursos.clear(); // limpiamos los cursos actuales

        CursoDTO body = new CursoDTO(); // si hace falta setear el mail, acá lo hacés

        body.setMail("juancruzm2004@gmail.com");
        ApiService apiService = ApiClient.getInstance().getApiService();

        Call<List<CronogramaCursoPlanoDTO>> call = apiService.obtenerCursosInscripto(body);


        call.enqueue(new Callback<List<CronogramaCursoPlanoDTO>>() {
            @Override
            public void onResponse(Call<List<CronogramaCursoPlanoDTO>> call, Response<List<CronogramaCursoPlanoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (CronogramaCursoPlanoDTO curso : response.body()) {

                        cursosmios.add(curso);
                        agregarCursoAMisCursos(curso);
                    }
                } else {
                    Toast.makeText(CursosActivity.this, "Error al obtener cursos", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<CronogramaCursoPlanoDTO>> call, Throwable t) {
                Toast.makeText(CursosActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void agregarCursoAlBody(curso curso) {
        View item = getLayoutInflater().inflate(R.layout.item_curso, contenedorDescubrir, false);

        TextView tvTitulo = item.findViewById(R.id.tvTituloCurso);
        TextView tvDescripcion = item.findViewById(R.id.tvDescripcionCurso);
        TextView tvPrecio = item.findViewById(R.id.tvPrecioCurso);
        TextView tvDescuento = item.findViewById(R.id.tvDescuentoCurso);
        TextView tvFecha = item.findViewById(R.id.tvFechaCurso);
        TextView tvModalidad = item.findViewById(R.id.tvModalidad);
        ImageView imgCurso = item.findViewById(R.id.imgCurso);
        ImageView icModalidad = item.findViewById(R.id.icModalidad);

        tvTitulo.setText(curso.getDescripcion());
        tvDescripcion.setText(curso.getDescripcion());
        tvPrecio.setText(String.valueOf(curso.getPrecio()));
        tvFecha.setText(String.valueOf(curso.getDuracion()));
        tvModalidad.setText(curso.getModalidad());
        tvDescuento.setText(""); // opcional



        // ver detalle curso
        item.setOnClickListener(v -> {
            if ("alumno".equals(tipoUsuario)) {
                Intent intent = new Intent(CursosActivity.this, DetalleCursosInscripcionActivity.class);
                intent.putExtra("curso", curso);
                intent.putExtra("idCurso", curso.getIdCurso());
                startActivity(intent);
            }
        });

        contenedorDescubrir.addView(item);
    }

    private void agregarCursoAMisCursos(CronogramaCursoPlanoDTO curso) {
        View item = getLayoutInflater().inflate(R.layout.item_mis_cursos, contenedorMisCursos, false);

        TextView tvTitulo = item.findViewById(R.id.tvTituloCurso);
        TextView tvDescripcion = item.findViewById(R.id.tvDescripcionCurso);
        TextView tvFecha = item.findViewById(R.id.tvFechaCurso);
        TextView tvEstadoSede = item.findViewById(R.id.tvEstadoSedeFecha);
        TextView tvModalidad = item.findViewById(R.id.tvModalidad);
        ImageView imgCurso = item.findViewById(R.id.imgCurso);
        ImageView icModalidad = item.findViewById(R.id.icModalidad);


        tvTitulo.setText(curso.getDescripcion());
        tvDescripcion.setText(curso.getDescripcion());
        //tvEstadoSede.setText(curso.sedes.get(0));
        tvFecha.setText(curso.getFechaInicio());
        tvModalidad.setText(curso.getModalidad());
       // imgCurso.setImageResource(curso.imagenResId);
     //   icModalidad.setImageResource(curso.iconoModalidadResId);

        item.setOnClickListener(v -> {
            Intent intent = new Intent(CursosActivity.this, DetalleMisCursosActivity.class);
            intent.putExtra("curso", curso);
            intent.putExtra("idCurso", curso.getIdCurso());
            startActivity(intent);
        });

        // Verificar fecha
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaFinCurso = formato.parse(curso.getFechaInicio());
            Date fechaActual = new Date();

            if (fechaFinCurso != null && fechaFinCurso.after(fechaActual)) {
                // En curso → mostrar sede (por ahora pongo la primera de la lista)
              //  tvEstadoSede.setText(curso.sedes.get(0));
                contenedorEnCurso.addView(item);
            } else {
                // Finalizado → mostrar la fecha de finalización
              //  tvEstadoSede.setText("Finalizado el: " + curso.fechaFin);
                contenedorFinalizados.addView(item);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            // Si falla el parseo, lo mando a En Curso por default
            contenedorEnCurso.addView(item);
        }
    }




}