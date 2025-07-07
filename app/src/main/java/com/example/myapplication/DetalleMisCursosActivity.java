package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dto.CursoDTO;


public class DetalleMisCursosActivity extends AppCompatActivity {

    CheckBox checkboxTarjeta, checkboxSaldoEnCuenta;
    ImageButton btnCerrar, qrButton;
    LinearLayout contenedorMisCursos;

    TextView tvTitulo, tvDescripcion, tvFecha, tvEstadoSede, tvModalidad;
    ImageView imgCurso, icModalidad;
    private static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_miscursos);

        // Vincular views
        checkboxTarjeta = findViewById(R.id.checkboxTarjeta);
        checkboxSaldoEnCuenta = findViewById(R.id.checkboxSaldoEnCuenta);
        btnCerrar = findViewById(R.id.btnCerrar);
        qrButton = findViewById(R.id.QR);

        // Configurar chequeo excluyente
        checkboxTarjeta.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) checkboxSaldoEnCuenta.setChecked(false);
        });

        checkboxSaldoEnCuenta.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) checkboxTarjeta.setChecked(false);
        });

        btnCerrar.setOnClickListener(v -> finish());

        qrButton.setOnClickListener(v -> abrirCamara());

        // Cargar datos si se pasa un curso desde otro activity
        CursoDTO curso = (CursoDTO) getIntent().getSerializableExtra("curso");
        if (curso != null) {
            cargarDatosCurso(curso);
        }

    }

    private void cargarDatosCurso(CursoDTO curso) {
        // Vincular Views
        TextView tvTitulo = findViewById(R.id.txtTitulo);
        TextView tvDescripcion = findViewById(R.id.descripcion);
        //TextView tvPrecio = findViewById(R.id.PrecioCurso);
        TextView tvModalidad = findViewById(R.id.ModalidadCurso);
        TextView tvDia = findViewById(R.id.DiaCurso);
        TextView tvHorario = findViewById(R.id.HorarioCurso);
        TextView tvFechaInicio = findViewById(R.id.FechaInicioCurso);
        TextView tvFechaFin = findViewById(R.id.FechaFinCurso);
        TextView tvObjetivo = findViewById(R.id.ObjetivoCurso);
        TextView tvTemas = findViewById(R.id.TemasCurso);
        TextView tvInsumos = findViewById(R.id.InsumosCurso);
        TextView tvMontoFinal = findViewById(R.id.PrecioCurso);
        ImageView imgCurso = findViewById(R.id.imgCurso);
        ImageButton icModalidad = findViewById(R.id.modalidad);
        ImageView fotoProfesor = findViewById(R.id.FotoProfesorCurso);
        TextView nombreProfesor = findViewById(R.id.NombreProfesorCurso);
        TextView descripcionProfesor = findViewById(R.id.DescripcionProfesorCurso);
        Spinner spinnerSede = findViewById(R.id.spinnerSedeCurso);

        // Cargar datos en los views
        tvTitulo.setText(curso.nombre);
        tvDescripcion.setText(curso.descripcion);
        //tvPrecio.setText(curso.precio);
        tvModalidad.setText(curso.modalidad);
        tvDia.setText(curso.dia);
        tvHorario.setText(curso.horario);
        tvFechaInicio.setText(curso.fechaInicio);
        tvFechaFin.setText(curso.fechaFin);
        tvObjetivo.setText(curso.objetivo);
        tvTemas.setText(curso.temas);
        tvInsumos.setText(curso.insumos);
        tvMontoFinal.setText(curso.montoFinal);
        imgCurso.setImageResource(curso.imagenResId);
        icModalidad.setImageResource(curso.iconoModalidadResId);
        fotoProfesor.setImageResource(curso.fotoProfesorResId);
        nombreProfesor.setText(curso.nombreProfesor);
        descripcionProfesor.setText(curso.descripcionProfesor);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_dark, curso.sedes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSede.setAdapter(adapter);
    }




    public void cerrar(View view){
        Intent intent = new Intent(this, CursosActivity.class);
        startActivity(intent);
    }
    private void abrirCamara() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
