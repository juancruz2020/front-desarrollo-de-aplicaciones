package com.example.myapplication;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

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


        //Boton para retroceder
        ImageButton btnBack = findViewById(R.id.btnCerrar);
        btnBack.setOnClickListener(v -> finish());



        CursoDTO curso = (CursoDTO) getIntent().getSerializableExtra("curso");
        if (curso != null) {
            cargarSedes(curso);
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

        // Capturar selección del spinner
        spSedeCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sedeSeleccionada = parent.getItemAtPosition(position).toString();

                // Si tiene "Sede Palermo" aplicar nuevo precio final
                if (sedeSeleccionada.contains("Sede Palermo")) {
                    int precio = Integer.parseInt(curso.precio.replace("$", "").replace(".", ""));
                    int precioConDescuento = precio - (precio * 30 / 100);
                    MontoFinalCurso.setText("$" + precioConDescuento);
                } else {
                    MontoFinalCurso.setText(curso.precio);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Editar Tarjeta
        Button btnEditar = findViewById(R.id.tvEditarNumeroTarjeta);
        btnEditar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.editar_tarjeta, null);
            builder.setView(dialogView);

            builder.setPositiveButton("Guardar", (dialog, which) -> {
                EditText etNumero = dialogView.findViewById(R.id.etNumeroTarjeta);
                String nuevoNumero = etNumero.getText().toString();
                // actualizar TextView u operar con el dato
                TextView tvNumero = findViewById(R.id.tvNumeroTarjeta);
                tvNumero.setText("**** **** **** " + nuevoNumero.substring(nuevoNumero.length()-4));
            });

            builder.setNegativeButton("Cancelar", null);

            builder.create().show();
        });


    }
    private void cargarSedes(CursoDTO curso) {
        // Armo una lista nueva con o sin descuento
        List<String> sedesConPrecio = new ArrayList<>();
        // Descuento
        int precio = Integer.parseInt(curso.precio.replace("$", "").replace(".", ""));
        int precioConDescuento = precio - (precio * 30 / 100);

        // Aplica descuento y cambia el nombre
        for (String sede : curso.sedes) {
            if (sede.equals("Sede Palermo")) {
                sedesConPrecio.add(sede + " (Total: $" + precioConDescuento + ")");
            } else {
                sedesConPrecio.add(sede);
            }
        }

        // Cargo spinner con la lista armada
        ArrayAdapter<String> adapterSedes = new ArrayAdapter<>(
                this,
                R.layout.spinner_item_dark,
                sedesConPrecio
        );
        adapterSedes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSedeCurso.setAdapter(adapterSedes);
        spSedeCurso.setSelection(0);
    }



}
