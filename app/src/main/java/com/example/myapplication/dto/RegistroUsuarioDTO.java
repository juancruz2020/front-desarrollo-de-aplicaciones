package com.example.myapplication.dto;

public class RegistroUsuarioDTO {
    private String mail;

    private String usuario;

    public RegistroUsuarioDTO(String mail, String usuario){
        this.mail = mail;
        this.usuario = usuario;
    }

    // Getters y setters
    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
}
