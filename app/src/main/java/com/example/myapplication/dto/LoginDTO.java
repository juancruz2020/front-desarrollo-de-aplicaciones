package com.example.myapplication.dto;

public class LoginDTO {
    private String credencialMail;
    private String credencialNikc;
    private String contrasena;

    public LoginDTO(String credencialMail,String credencialNikc, String contrasena) {
        this.credencialMail = credencialMail;
        this.credencialNikc = credencialNikc;
        this.contrasena = contrasena;
    }
    public LoginDTO() {}


}
