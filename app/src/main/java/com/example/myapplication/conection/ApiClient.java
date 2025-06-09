package com.example.myapplication.conection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://192.168.4.223:8080/";
    private static ApiClient instance;
    private final ApiService apiService;

    private ApiClient() {
        // Crear Gson con modo lenient activado
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // Configurar OkHttpClient con timeout extendido
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)  // Tiempo para conectar
                .readTimeout(120, TimeUnit.SECONDS)    // Tiempo para leer la respuesta
                .writeTimeout(60, TimeUnit.SECONDS)    // Tiempo para enviar la petición
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)  // Usar OkHttpClient con timeout configurado
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
