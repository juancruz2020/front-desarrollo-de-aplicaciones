package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dto.CursoDTO;

public class DetalleCursosInscripcionActivity extends AppCompatActivity {

    Spinner spSedeCurso;

    // Views para hardcodear datos
    TextView txtTitulo, descripcion, tvTiempoValor, tvTiempoUnidad,
            PrecioCurso, ModalidadCurso, DiaCurso, HorarioCurso,
            FechaInicioCurso, FechaFinCurso, ObjetivoCurso, TemasCurso,
            InsumosCurso, MedioDePagoCurso, MontoFinalCurso;

    ImageView imgReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cursos_inscripcion);

        // Vincular views
        spSedeCurso = findViewById(R.id.spinnerSedeCurso);
        txtTitulo = findViewById(R.id.txtTitulo);
        descripcion = findViewById(R.id.descripcion);
        tvTiempoValor = findViewById(R.id.tvTiempoValor);
        tvTiempoUnidad = findViewById(R.id.tvTiempoUnidad);
        PrecioCurso = findViewById(R.id.PrecioCurso);
        ModalidadCurso = findViewById(R.id.ModalidadCurso);
        DiaCurso = findViewById(R.id.DiaCurso);
        HorarioCurso = findViewById(R.id.HorarioCurso);
        FechaInicioCurso = findViewById(R.id.FechaInicioCurso);
        FechaFinCurso = findViewById(R.id.FechaFinCurso);
        ObjetivoCurso = findViewById(R.id.ObjetivoCurso);
        TemasCurso = findViewById(R.id.TemasCurso);
        InsumosCurso = findViewById(R.id.InsumosCurso);
        MedioDePagoCurso = findViewById(R.id.MedioDePagoCurso);
        MontoFinalCurso = findViewById(R.id.MontoFinalCurso);
        imgReceta = findViewById(R.id.imgReceta);


        // Cargar sedes al spinner
        cargarSedes();

        //Boton para retroceder
        ImageButton btnBack = findViewById(R.id.btnCerrar);
        btnBack.setOnClickListener(v -> finish());

        // Capturar selección del spinner
        spSedeCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sedeSeleccionada = parent.getItemAtPosition(position).toString();
                Log.d("SPINNER_SEDE", "Seleccionaste: " + sedeSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // nada
            }
        });

        CursoDTO curso = (CursoDTO) getIntent().getSerializableExtra("curso");

        if (curso != null) {
            txtTitulo.setText(curso.nombre);
            descripcion.setText(curso.descripcion);
            tvTiempoValor.setText("3");
            tvTiempoUnidad.setText("Horas");
            PrecioCurso.setText(curso.precio);
            ModalidadCurso.setText(curso.modalidad);
            DiaCurso.setText(curso.dia);
            HorarioCurso.setText(curso.horario);
            FechaInicioCurso.setText(curso.fechaInicio);
            FechaFinCurso.setText(curso.fechaFin);
            ObjetivoCurso.setText(curso.objetivo);
            TemasCurso.setText(curso.temas);
            InsumosCurso.setText(curso.insumos);
            MontoFinalCurso.setText(curso.montoFinal);
            imgReceta.setImageResource(curso.imagenResId);
            ImageView fotoProfesor = findViewById(R.id.FotoProfesorCurso);
            TextView nombreProfesor = findViewById(R.id.NombreProfesorCurso);
            TextView descripcionProfesor = findViewById(R.id.DescripcionProfesorCurso);

            fotoProfesor.setImageResource(curso.fotoProfesorResId);
            nombreProfesor.setText(curso.nombreProfesor);
            descripcionProfesor.setText(curso.descripcionProfesor);


        }


    }

    private void cargarSedes() {
        ArrayAdapter<String> adapterSedes = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Sede Palermo", "Sede Caballito", "Sede Microcentro", "Sede Devoto"}
        );
        adapterSedes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSedeCurso.setAdapter(adapterSedes);
        spSedeCurso.setSelection(0); // preselecciona Palermo
    }


}
