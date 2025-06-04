package com.example.myapplication.dto;

public class CambioContrasenaDTO {
    private String mail;
    private String codigo;
    private String contrasena;

    public CambioContrasenaDTO(String mail, String codigo, String contrasena) {
        this.mail = mail;
        this.codigo = codigo;
        this.contrasena = contrasena;
    }
}
