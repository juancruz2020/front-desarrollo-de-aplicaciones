package com.example.myapplication.conection;

import com.example.myapplication.dto.*;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;


import java.util.List;
public interface ApiService {
    // POST /usuarios/registro
    @POST("usuarios/registro")
    Call<ResponseBody> registrarUsuario(@Body RegistroUsuarioDTO dto);

    // POST /usuarios/validar_codigo
    @POST("usuarios/validar_codigo")
    Call<ResponseBody> validarCodigo(@Body CodigoDTO dto);

    // POST /usuarios/registro-datos
    @POST("usuarios/registro-datos")
    Call<String> registrarDatosPersonales(@Body RegistroDatosPersonalesDTO dto);

    // POST /usuarios/registro-alumno con Multipart (archivos + datos)
    @Multipart
    @POST("usuarios/registro-alumno")
    Call<ResponseBody> registrarAlumno(
            @Part("datos") RequestBody datos,
            @Part MultipartBody.Part dniFrente,
            @Part MultipartBody.Part dniDorso
    );

    // POST /usuarios/login
    @POST("usuarios/login")
    Call<ResponseBody> login(@Body LoginDTO dto);

    // POST /usuarios/cambiar-contrasena-codigo
    @POST("usuarios/cambiar-contrasena-codigo")
    Call<ResponseBody> cambiarContrasenaCodigo(@Body MailCodigoDto dto);

    // POST /usuarios/cambiar-contrasena
    @POST("usuarios/cambiar-contrasena")
    Call<ResponseBody> cambiarContrasena(@Body CambioContrasenaDTO dto);

}
