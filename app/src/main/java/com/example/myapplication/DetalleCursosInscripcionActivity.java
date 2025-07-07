package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.conection.ApiService;
import com.example.myapplication.dto.CursoPlanoConCronogramaDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class DetalleCursosInscripcionActivity extends AppCompatActivity {

    Spinner spSedeCurso;

    TextView txtTitulo, descripcion, tvTiempoValor, tvTiempoUnidad,
            PrecioCurso, ModalidadCurso, DiaCurso, HorarioCurso,
            FechaInicioCurso, FechaFinCurso, ObjetivoCurso, TemasCurso,
            InsumosCurso, MedioDePagoCurso, MontoFinalCurso;

    ImageView imgReceta;

    CursoPlanoConCronogramaDTO curso;

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

        // Botón para retroceder
        ImageButton btnBack = findViewById(R.id.btnCerrar);
        btnBack.setOnClickListener(v -> finish());

        // Obtener idCurso desde el Intent
        int idCurso = getIntent().getIntExtra("idCurso", -1);
        if (idCurso == -1) {
            Toast.makeText(this, "No se recibió el id del curso", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarCursoDesdeApi(idCurso);

        // Editar Tarjeta
        Button btnEditar = findViewById(R.id.tvEditarNumeroTarjeta);
        btnEditar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.editar_tarjeta, null);
            builder.setView(dialogView);

            builder.setPositiveButton("Guardar", (dialog, which) -> {
                EditText etNumero = dialogView.findViewById(R.id.etNumeroTarjeta);
                String nuevoNumero = etNumero.getText().toString();
                TextView tvNumero = findViewById(R.id.tvNumeroTarjeta);
                if (nuevoNumero.length() >= 4) {
                    tvNumero.setText("**** **** **** " + nuevoNumero.substring(nuevoNumero.length() - 4));
                }
            });

            builder.setNegativeButton("Cancelar", null);
            builder.create().show();
        });
    }

    private void cargarCursoDesdeApi(int idCurso) {
        ApiService apiService = ApiClient.getInstance().getApiService();

        com.example.myapplication.dto.IdCursosDTO idCursosDTO = new com.example.myapplication.dto.IdCursosDTO();
        idCursosDTO.setId(idCurso);

        Call<CursoPlanoConCronogramaDTO> call = apiService.obtenerUnCurso(idCursosDTO);

        call.enqueue(new Callback<CursoPlanoConCronogramaDTO>() {
            @Override
            public void onResponse(Call<CursoPlanoConCronogramaDTO> call, Response<CursoPlanoConCronogramaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    curso = response.body();
                    mostrarDatosCurso(curso);
                } else {
                    Toast.makeText(DetalleCursosInscripcionActivity.this, "Error al cargar el curso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CursoPlanoConCronogramaDTO> call, Throwable t) {
                Toast.makeText(DetalleCursosInscripcionActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void mostrarDatosCurso(CursoPlanoConCronogramaDTO curso) {
        txtTitulo.setText(curso.getDescripcion());
        descripcion.setText(curso.getContenidos());

        tvTiempoValor.setText(String.valueOf(curso.getDuracion()));
        tvTiempoUnidad.setText("Horas");

        PrecioCurso.setText("$" + curso.getPrecio());
        ModalidadCurso.setText(curso.getModalidad());

        DiaCurso.setText(""); // No disponible en DTO
        HorarioCurso.setText(""); // No disponible en DTO

        // Fechas como String
        FechaInicioCurso.setText(curso.getFechaInicio() != null ? curso.getFechaInicio() : "");
        FechaFinCurso.setText(curso.getFechaFin() != null ? curso.getFechaFin() : "");

        ObjetivoCurso.setText(curso.getContenidos()); // Adaptar según datos reales
        TemasCurso.setText(curso.getDescripcion());   // Adaptar según datos reales
        InsumosCurso.setText(curso.getRequerimientos());
        MedioDePagoCurso.setText("$" + curso.getPrecio());
        MontoFinalCurso.setText("$" + curso.getPrecio());

        cargarSede(curso);
    }

    private void cargarSede(CursoPlanoConCronogramaDTO curso) {
        List<String> sedesLista = new ArrayList<>();
        sedesLista.add(curso.getNombreSede() != null ? curso.getNombreSede() : "Sede no disponible");

        ArrayAdapter<String> adapterSedes = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                sedesLista
        );
        adapterSedes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSedeCurso.setAdapter(adapterSedes);
        spSedeCurso.setSelection(0);
        spSedeCurso.setEnabled(false);  // deshabilitamos porque sólo hay una sede

        // Precio final igual al precio base (sin descuentos ni variaciones)
        MontoFinalCurso.setText("$" + curso.getPrecio());
    }
}
