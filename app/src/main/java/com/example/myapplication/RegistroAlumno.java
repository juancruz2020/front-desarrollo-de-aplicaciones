package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.dto.RegistroAlumnoDTO;
import com.google.gson.Gson;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroAlumno extends AppCompatActivity {

    private static final int PICK_FRENTE = 1;
    private static final int PICK_DORSO = 2;

    private EditText nroTarjeta, nTramite, corriente;
    private Button btnCrearCuenta, fotoFrente, fotoDorso;
    private ImageButton btnVolver;
    private Button volverInicio;

    private ImageView imageViewFrente, imageViewDorso;

    private Uri uriFrente, uriDorso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alumno);

        nroTarjeta = findViewById(R.id.nroTarjeta);
        nTramite = findViewById(R.id.nTramite);
        corriente = findViewById(R.id.corriente);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        fotoFrente = findViewById(R.id.fotoFrente);
        fotoDorso = findViewById(R.id.fotoDorso);
        btnVolver = findViewById(R.id.btn_volver);
        volverInicio = findViewById(R.id.volverInicio);
        imageViewFrente = findViewById(R.id.imageViewFrente);
        imageViewDorso = findViewById(R.id.imageViewDorso);

        fotoFrente.setOnClickListener(v -> seleccionarImagen(PICK_FRENTE));
        fotoDorso.setOnClickListener(v -> seleccionarImagen(PICK_DORSO));
        btnCrearCuenta.setOnClickListener(this::registrarAlumno);
    }

    public void volverARegistro(View view) {
        finish();
    }

    public void inicio(View view) {
        startActivity(new Intent(this, Login.class));
    }

    private void seleccionarImagen(int codigo) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), codigo);
    }

    @Override
    protected void onActivityResult(int codigo, int resultCode, @Nullable Intent data) {
        super.onActivityResult(codigo, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (codigo == PICK_FRENTE) {
                Uri selectedImageUri = data.getData();
                uriFrente = selectedImageUri;
                imageViewFrente.setImageURI(uriFrente);
                uriFrente = data.getData();
                Toast.makeText(this, "Foto frente seleccionada", Toast.LENGTH_SHORT).show();
            } else if (codigo == PICK_DORSO) {
                Uri selectedImageUri = data.getData();
                uriDorso = selectedImageUri;
                imageViewDorso.setImageURI(uriDorso);
                uriDorso = data.getData();
                Toast.makeText(this, "Foto dorso seleccionada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void registrarAlumno(View view) {
        String tarjeta = nroTarjeta.getText().toString().trim();
        String tramite = nTramite.getText().toString().trim();
        String cuenta = corriente.getText().toString().trim();

        if (tarjeta.isEmpty() || tramite.isEmpty() || cuenta.isEmpty() || uriFrente == null || uriDorso == null) {
            Toast.makeText(this, "Complete todos los campos y seleccione ambas fotos", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getSharedPreferences("mis_preferencias", MODE_PRIVATE);
        String emailGuardado = preferences.getString("email", "");

        BigDecimal bdCuenta;
        try {
            String cuentaNormalized = cuenta.replace(",", ".");
            bdCuenta = new BigDecimal(cuentaNormalized);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El valor ingresado en corriente no es válido", Toast.LENGTH_SHORT).show();
            return;
        }

        RegistroAlumnoDTO dto = new RegistroAlumnoDTO(emailGuardado, tramite, tarjeta, bdCuenta);

        String json = new Gson().toJson(dto);
        RequestBody datosJson = RequestBody.create(json, MediaType.parse("application/json"));

        try {
            MultipartBody.Part archivoFrente = crearParteArchivo("DniFrente", uriFrente);
            MultipartBody.Part archivoDorso = crearParteArchivo("DniDorso", uriDorso);

            ApiClient.getInstance().getApiService()
                    .registrarAlumno(datosJson, archivoFrente, archivoDorso)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(RegistroAlumno.this, "Alumno registrado", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                                Intent intent = new Intent(RegistroAlumno.this, Login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistroAlumno.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(RegistroAlumno.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

        } catch (Exception e) {
            Toast.makeText(this, "Error preparando archivos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private MultipartBody.Part crearParteArchivo(String nombreParte, Uri uri) throws Exception {
        String nombreArchivo = getNombreArchivo(uri);
        String extension = "";

        int i = nombreArchivo.lastIndexOf('.');
        if (i > 0) {
            extension = nombreArchivo.substring(i);
        }

        File archivo = File.createTempFile("temp", extension, getCacheDir());

        try (InputStream is = getContentResolver().openInputStream(uri);
             OutputStream os = new java.io.FileOutputStream(archivo)) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
        }

        RequestBody body = RequestBody.create(archivo, MediaType.parse("image/*"));
        return MultipartBody.Part.createFormData(nombreParte, nombreArchivo, body);
    }

    private String getNombreArchivo(Uri uri) {
        String nombre = "foto.jpg";
        try (android.database.Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int nombreIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (nombreIndex >= 0) {
                    nombre = cursor.getString(nombreIndex);
                }
            }
        }
        return nombre;
    }

    private void limpiarCampos() {
        nroTarjeta.setText("");
        nTramite.setText("");
        corriente.setText("");
        uriFrente = null;
        uriDorso = null;
    }
}