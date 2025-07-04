package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.conection.ApiClient;
import com.example.myapplication.conection.ApiService;
import com.example.myapplication.dto.IngredienteDTO;
import com.example.myapplication.dto.PasoDTO;
import com.example.myapplication.dto.RecetaDTO;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CargarRecetaActivity extends AppCompatActivity {

    EditText etNombre, etDescripcion, etTiempo, etIngrediente;
    Spinner spTipoPlato, spTiempoUnidad, spUnidadIngrediente;
    TextView tvPorciones, tvCantidad;
    ImageButton btnMasPorciones, btnMenosPorciones, btnMasCantidad, btnMenosCantidad;
    Button btnAgregarIngrediente;
    LinearLayout layoutIngredientes;
    double cantidadActual = 1;
    String unidadActual = "g";
    View ingredienteEnEdicion = null;
    EditText etInstruccion;
    Button btnAgregarPaso, btnAgregarMedia;
    LinearLayout layoutPasos;
    int contadorPasos = 1;
    View pasoEnEdicion = null;
    private static final int REQUEST_MEDIA_PICK = 101;
    private static final int REQUEST_PORTADA_PICK = 102;
    LinearLayout layoutArchivoAdjunto;
    TextView tvNombreArchivo;
    ImageButton btnEliminarArchivo;
    Uri uriArchivoSeleccionado = null;

    private int currentPasoIndex = -1;
    private Uri mediaUri = null;
    Button btnAgregarPortada, btnSubirReceta;
    Uri uriPortada = null;
    LinearLayout layoutPortadas;
    List<Uri> portadasUri = new ArrayList<>();
    private List<Paso> pasosList = new ArrayList<>();
    private List<Ingrediente> listaIngredientes = new ArrayList<>(); //Almacena los ingredientes
    Map<String, Double> incrementos = new HashMap<>(); //Maneja los incrementos segun la unidad
    Map<String, Double> valoresDefault = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_receta);

        inicializarUI();
        cargarUnidades();
        configurarEventos();
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
    private void inicializarUI() {
        etNombre = findViewById(R.id.etNombrePlato);
        etDescripcion = findViewById(R.id.etDescripcion);
        etTiempo = findViewById(R.id.etTiempoPreparacion);
        etIngrediente = findViewById(R.id.etIngrediente);

        spTipoPlato = findViewById(R.id.spinnerTipoPlato);
        spTiempoUnidad = findViewById(R.id.spinnerTiempo);
        spUnidadIngrediente = findViewById(R.id.spinnerUnidad);

        tvPorciones = findViewById(R.id.tvCantidadPorciones);
        btnMasPorciones = findViewById(R.id.btnMasPorciones);
        btnMenosPorciones = findViewById(R.id.btnMenosPorciones);

        tvCantidad = findViewById(R.id.tvCantidad);
        btnMasCantidad = findViewById(R.id.btnMasCantidad);
        btnMenosCantidad = findViewById(R.id.btnMenosCantidad);

        btnAgregarIngrediente = findViewById(R.id.btnAgregarIngrediente);
        layoutIngredientes = findViewById(R.id.layoutIngredientes);

        etInstruccion = findViewById(R.id.etInstruccion);
        btnAgregarPaso = findViewById(R.id.btnAgregarPaso);
        btnAgregarMedia = findViewById(R.id.btnAgregarMedia);
        layoutPasos = findViewById(R.id.layoutPasos);

        layoutArchivoAdjunto = findViewById(R.id.layoutArchivoAdjunto);
        tvNombreArchivo = findViewById(R.id.tvNombreArchivo);
        btnEliminarArchivo = findViewById(R.id.btnEliminarArchivo);

        layoutPortadas = findViewById(R.id.layoutPortadas);
        btnAgregarPortada = findViewById(R.id.btnAgregarPortada);
        btnSubirReceta = findViewById(R.id.btnSubirReceta);

        //Boton para retroceder
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        //Lista desplegable
        ArrayAdapter<String> adapterPlatos = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Entradas", "Pastas", "Carnes", "Pescados", "Ensaladas", "Postres", "Bebidas", "Guarnicion"});
        adapterPlatos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoPlato.setAdapter(adapterPlatos);
        //Lista desplegable de tiempo
        ArrayAdapter<String> adapterTiempo = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Min", "Hor"});
        adapterTiempo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTiempoUnidad.setAdapter(adapterTiempo);
        //Lista desplegable de unidades
        ArrayAdapter<String> adapterUnidades = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"g", "kg", "ml", "u", "cda", "cdita", "pizca", "taza", "diente", "hoja", "rama"});
        adapterUnidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnidadIngrediente.setAdapter(adapterUnidades);
        //Esto abre el selector de archivos para elegir una portada.
        btnAgregarPortada.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
            startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen o video"), REQUEST_PORTADA_PICK);
        });
    }
    //Valores por defecto y su incremento por unidad.
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

        unidadActual = "g";
        cantidadActual = valoresDefault.get(unidadActual);
        actualizarCantidadTexto();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void configurarEventos() {
        //Actualiza el valor cantidad
        spUnidadIngrediente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unidadActual = parent.getItemAtPosition(position).toString();
                cantidadActual = valoresDefault.get(unidadActual);
                actualizarCantidadTexto();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnMasPorciones.setOnClickListener(v -> {
            int actual = Integer.parseInt(tvPorciones.getText().toString());
            tvPorciones.setText(String.valueOf(actual + 1));
        });

        btnMenosPorciones.setOnClickListener(v -> {
            int actual = Integer.parseInt(tvPorciones.getText().toString());
            if (actual > 1) tvPorciones.setText(String.valueOf(actual - 1));
        });

        btnMasCantidad.setOnClickListener(v -> {
            cantidadActual += incrementos.get(unidadActual);
            actualizarCantidadTexto();
        });

        btnMenosCantidad.setOnClickListener(v -> {
            cantidadActual = Math.max(0, cantidadActual - incrementos.get(unidadActual));
            actualizarCantidadTexto();
        });

        btnAgregarIngrediente.setOnClickListener(v -> {
            String nombre = etIngrediente.getText().toString().trim();
            if (!nombre.isEmpty()) {
                agregarIngrediente(nombre, cantidadActual, unidadActual);
                listaIngredientes.add(new Ingrediente(nombre, cantidadActual, unidadActual));
                etIngrediente.setText("");
                cantidadActual = valoresDefault.get(unidadActual);
                actualizarCantidadTexto();
                spUnidadIngrediente.setSelection(getIndexFromSpinner(spUnidadIngrediente, unidadActual));
            }
        });

        btnAgregarPaso.setOnClickListener(v -> {
            String descripcion = etInstruccion.getText().toString().trim();
            if (descripcion.isEmpty()) return;

            String media = (mediaUri != null) ? mediaUri.toString() : null;

            if (currentPasoIndex == -1) {
                // Nuevo paso
                Paso nuevoPaso = new Paso(0, descripcion, media);
                pasosList.add(nuevoPaso);
            } else {
                // Editar paso
                pasosList.get(currentPasoIndex).descripcion = descripcion;
                pasosList.get(currentPasoIndex).mediaPath = media;
                currentPasoIndex = -1;
            }

            actualizarListaPasos();
            limpiarCamposPaso();
        });


        btnAgregarMedia.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
            startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen o video"), REQUEST_MEDIA_PICK);
        });


        btnEliminarArchivo.setOnClickListener(v -> {
            layoutArchivoAdjunto.setVisibility(View.GONE);
            mediaUri = null;
        });

        btnAgregarPortada.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
            startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen o video"), REQUEST_PORTADA_PICK);
        });

        btnSubirReceta.setOnClickListener(v -> {
            Receta receta = new Receta();
            receta.nombrePlato = etNombre.getText().toString();
            receta.descripcion = etDescripcion.getText().toString();
            receta.cantidadPorciones = Integer.parseInt(tvPorciones.getText().toString());
            receta.tiempoValor = etTiempo.getText().toString();
            receta.tiempoUnidad = spTiempoUnidad.getSelectedItem().toString();
            receta.ingredientes = listaIngredientes;
            receta.pasos = pasosList;
            receta.portadaPath = uriPortada != null ? uriPortada.toString() : null;
            String categoria = "Pastas";
            String nickname = "Usuario 1";

            AtomicInteger indexPasos = new AtomicInteger(1);

            List<PasoDTO> pasosDto = receta.pasos.stream()
                    .map(p -> {
                        p.setNumeroPaso(indexPasos.get());
                        return new PasoDTO(indexPasos.getAndIncrement(), p.descripcion, p.mediaPath);
                    })
                    .collect(Collectors.toList());


            List<IngredienteDTO> ingredientesDto = receta.ingredientes.stream()
                    .map(i -> {
                        return new IngredienteDTO(i.nombre, (int) i.cantidad, i.unidad, " ");
                    })
                    .collect(Collectors.toList());

            RecetaDTO dto = new RecetaDTO(nickname, receta.nombrePlato, categoria, ingredientesDto, pasosDto, receta.descripcion);

            Gson gson = new Gson();
            String recetaJson = gson.toJson(dto);

            RequestBody recetaRequestBody = RequestBody.create(
                    recetaJson,
                    MediaType.parse("application/json; charset=utf-8")
            );

            MultipartBody.Part datosPart = MultipartBody.Part.createFormData("datos", null, recetaRequestBody);

            // Por ahora no estás enviando imágenes, así que podés dejarla vacía
            List<MultipartBody.Part> imagenesParts = new ArrayList<>();

            Log.d("DEBUG", "Ingredientes: " + new Gson().toJson(listaIngredientes));
            Log.d("DEBUG", "JSON enviado: " + recetaJson);

            ApiService apiService = ApiClient.getInstance().getApiService();
            Call<ResponseBody> call = apiService.cargarReceta(datosPart, imagenesParts);

            call.enqueue(new Callback<ResponseBody>() {
                 @Override
                 public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     if (response.isSuccessful()) {
                         Log.d("API", "Receta subida con éxito");
                     } else {
                         Log.e("API", "Error al subir receta: " + response.code());
                     }
                 }

                 @Override
                 public void onFailure(Call<ResponseBody> call, Throwable t) {
                     Log.e("API", "Fallo de conexión: " + t.getMessage());
                 }
             });

            Intent intent = new Intent(CargarRecetaActivity.this, DetalleRecetaActivity.class);
            intent.putExtra("receta", receta);
            startActivity(intent);
        });





    }

    private void actualizarCantidadTexto() {
        //Muestra la cantidad del ingrediente segun su unidad
        boolean decimal = unidadActual.equals("kg") || unidadActual.equals("cda")
                || unidadActual.equals("cdita") || unidadActual.equals("taza");

        String formato = decimal ? "%.2f" : "%.0f";
        tvCantidad.setText(String.format(Locale.US, formato, cantidadActual));
    }
    //Agrega un view al layout de ingredientes y permite modificarlos
    private void agregarIngrediente(String nombre, double cantidad, String unidad) {
        final String texto = String.format(Locale.US, "%.0f %s | %s", cantidad, unidad, nombre);

        if (ingredienteEnEdicion != null) {
            // Editando uno existente
            TextView tvDetalle = ingredienteEnEdicion.findViewById(R.id.tvDetalleIngrediente);
            tvDetalle.setText(texto);
            ingredienteEnEdicion = null;
            btnAgregarIngrediente.setText("Agregar Ingrediente");
            return;
        }

        // Crear nuevo ingrediente
        View item = LayoutInflater.from(this).inflate(R.layout.item_ingrediente_cargar_receta, layoutIngredientes, false);
        TextView tvDetalle = item.findViewById(R.id.tvDetalleIngrediente);
        ImageButton btnEditar = item.findViewById(R.id.btnEditarIngrediente);
        ImageButton btnEliminar = item.findViewById(R.id.btnEliminarIngrediente);

        tvDetalle.setText(texto);

        btnEditar.setOnClickListener(v -> {
            // Parsear el texto y llenar campos
            String[] partes = texto.split(" \\| ");
            if (partes.length == 2) {
                String[] cantUnidad = partes[0].split(" ");
                if (cantUnidad.length == 2) {
                    cantidadActual = Double.parseDouble(cantUnidad[0]);
                    unidadActual = cantUnidad[1];
                    String nombreEditado = partes[1];

                    etIngrediente.setText(nombreEditado);
                    spUnidadIngrediente.setSelection(getIndexFromSpinner(spUnidadIngrediente, unidadActual));
                    actualizarCantidadTexto();

                    ingredienteEnEdicion = item;
                    btnAgregarIngrediente.setText("Actualizar Ingrediente");
                }
            }
        });

        btnEliminar.setOnClickListener(v -> layoutIngredientes.removeView(item));

        layoutIngredientes.addView(item);
    }
    //Devuelve el indice cuando se edita un elemento
    private int getIndexFromSpinner(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0;
    }
    //para editar, eliminar o agregar media
    private void agregarPaso(String descripcion, @Nullable Uri uriMedia) {
        if (pasoEnEdicion != null) {
            TextView tvPaso = pasoEnEdicion.findViewById(R.id.tvDescripcionPaso);
            ImageView imgMedia = pasoEnEdicion.findViewById(R.id.imgPasoMedia);

            tvPaso.setText("Paso " + pasoEnEdicion.getTag() + ": " + descripcion);

            if (uriMedia != null) {
                imgMedia.setImageURI(uriMedia);
                imgMedia.setVisibility(View.VISIBLE);
            }

            pasoEnEdicion = null;
            btnAgregarPaso.setText("Agregar Paso");
            return;
        }

        View itemPaso = LayoutInflater.from(this).inflate(R.layout.item_paso, layoutPasos, false);

        int numero = contadorPasos++;
        itemPaso.setTag(numero);

        TextView tvPaso = itemPaso.findViewById(R.id.tvDescripcionPaso);
        ImageView imgMedia = itemPaso.findViewById(R.id.imgPasoMedia);
        ImageButton btnEditar = itemPaso.findViewById(R.id.btnEditarPaso);
        ImageButton btnEliminar = itemPaso.findViewById(R.id.btnEliminarPaso);

        tvPaso.setText("Paso " + numero + ": " + descripcion);

        if (uriMedia != null) {
            imgMedia.setImageURI(uriMedia);
            imgMedia.setVisibility(View.VISIBLE);
            imgMedia.setTag(uriMedia.toString()); // Guarda el URI como String
            itemPaso.setTag(R.id.imgPasoMedia, uriMedia); // ✔ guarda el URI en el paso

        }


        btnEditar.setOnClickListener(v -> {
            // Recuperar descripción
            String texto = tvPaso.getText().toString();
            String sinPrefijo = texto.substring(texto.indexOf(":") + 1).trim();
            etInstruccion.setText(sinPrefijo);

            // Recuperar URI guardado en el paso
            Object uriGuardado = itemPaso.getTag(R.id.imgPasoMedia);
            if (uriGuardado != null) {
                uriArchivoSeleccionado = Uri.parse(uriGuardado.toString());
                tvNombreArchivo.setText(obtenerNombreArchivo(uriArchivoSeleccionado));
                layoutArchivoAdjunto.setVisibility(View.VISIBLE);
            } else {
                uriArchivoSeleccionado = null;
                layoutArchivoAdjunto.setVisibility(View.GONE);
            }



            pasoEnEdicion = itemPaso;
            btnAgregarPaso.setText("Actualizar Paso");
        });



        btnEliminar.setOnClickListener(v -> {
            layoutPasos.removeView(itemPaso);
            actualizarNumerosPasos();
        });

        layoutPasos.addView(itemPaso);

        uriArchivoSeleccionado = null;
        layoutArchivoAdjunto.setVisibility(View.GONE);

        btnEliminarArchivo.setOnClickListener(v -> {
            layoutArchivoAdjunto.setVisibility(View.GONE);
            mediaUri = null;
        });


    }



    private void actualizarNumerosPasos() {
        int nuevoContador = 1;
        for (int i = 0; i < layoutPasos.getChildCount(); i++) {
            View paso = layoutPasos.getChildAt(i);
            TextView tvPaso = paso.findViewById(R.id.tvDescripcionPaso);
            paso.setTag(nuevoContador);
            String textoViejo = tvPaso.getText().toString();
            String nuevoTexto = "Paso " + nuevoContador + ": " + textoViejo.substring(textoViejo.indexOf(":") + 1).trim();
            tvPaso.setText(nuevoTexto);
            nuevoContador++;
        }
        contadorPasos = nuevoContador;
    }
    //Carga portada, multimedia de pasos y muestra los nombres.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_MEDIA_PICK && resultCode == RESULT_OK && data != null) {
            uriArchivoSeleccionado = data.getData();
            mediaUri = uriArchivoSeleccionado;
            final int takeFlags = data.getFlags() &
                    (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            if (takeFlags != 0 && uriArchivoSeleccionado != null) {
                try {
                    getContentResolver().takePersistableUriPermission(uriArchivoSeleccionado, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
            if (uriArchivoSeleccionado != null) {
                String nombre = obtenerNombreArchivo(uriArchivoSeleccionado);
                tvNombreArchivo.setText(nombre);
                layoutArchivoAdjunto.setVisibility(View.VISIBLE);
            }
        }

        if (requestCode == REQUEST_PORTADA_PICK && resultCode == RESULT_OK && data != null) {
            uriPortada = data.getData();

            if (uriPortada != null) {
                final int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                try {
                    getContentResolver().takePersistableUriPermission(uriPortada, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }

                portadasUri.add(uriPortada);

                View pildora = LayoutInflater.from(this).inflate(R.layout.item_portada, layoutPortadas, false);
                TextView tvArchivo = pildora.findViewById(R.id.tvArchivoPortada);
                ImageButton btnEliminar = pildora.findViewById(R.id.btnEliminarPortada);

                tvArchivo.setText(obtenerNombreArchivo(uriPortada));
                btnEliminar.setOnClickListener(v -> {
                    layoutPortadas.removeView(pildora);
                    portadasUri.remove(uriPortada);
                });

                layoutPortadas.addView(pildora);
            }
        }



    }

    private String obtenerNombreArchivo(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) result = cursor.getString(index);
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void actualizarListaPasos() {
        layoutPasos.removeAllViews();

        for (int i = 0; i < pasosList.size(); i++) {
            Paso paso = pasosList.get(i);
            View itemPaso = LayoutInflater.from(this).inflate(R.layout.item_paso, null);

            TextView tvDescripcionPaso = itemPaso.findViewById(R.id.tvDescripcionPaso);
            tvDescripcionPaso.setText("Paso " + (i + 1) + ": " + paso.descripcion);

            ImageView imgMedia = itemPaso.findViewById(R.id.imgPasoMedia);
            TextView tvNombreArchivo = itemPaso.findViewById(R.id.tvNombreArchivo);
            LinearLayout layoutArchivo = itemPaso.findViewById(R.id.layoutArchivoAdjunto);

            if (paso.mediaPath != null) {
                Uri mediaUri = Uri.parse(paso.mediaPath);
                imgMedia.setImageURI(mediaUri);
                imgMedia.setVisibility(View.VISIBLE);

                layoutArchivo.setVisibility(View.VISIBLE);
                tvNombreArchivo.setText(new File(paso.mediaPath).getName());

                // Guardar URI en tag del itemPaso
                itemPaso.setTag(R.id.imgPasoMedia, mediaUri);
            } else {
                imgMedia.setVisibility(View.GONE);
                layoutArchivo.setVisibility(View.GONE);
            }

            ImageButton btnEditarPaso = itemPaso.findViewById(R.id.btnEditarPaso);
            ImageButton btnEliminarPaso = itemPaso.findViewById(R.id.btnEliminarPaso);

            int finalI = i;
            btnEditarPaso.setOnClickListener(v -> {
                etInstruccion.setText(paso.descripcion);
                if (paso.mediaPath != null) {
                    layoutArchivoAdjunto.setVisibility(View.VISIBLE);
                    tvNombreArchivo.setText(new File(paso.mediaPath).getName());
                    uriArchivoSeleccionado = Uri.parse(paso.mediaPath);
                    mediaUri = uriArchivoSeleccionado;
                } else {
                    layoutArchivoAdjunto.setVisibility(View.GONE);
                    uriArchivoSeleccionado = null;
                    mediaUri = null;
                }
                currentPasoIndex = finalI;
            });



            btnEliminarPaso.setOnClickListener(v -> {
                pasosList.remove(finalI);
                actualizarListaPasos();
            });

            layoutPasos.addView(itemPaso);
        }
    }


    private void limpiarCamposPaso() {
        etInstruccion.setText("");
        mediaUri = null;
        layoutArchivoAdjunto.setVisibility(View.GONE);
    }


}

