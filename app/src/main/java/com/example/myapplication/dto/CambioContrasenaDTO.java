package com.example.myapplication.dto;

public class CambioContrasenaDTO {
    private String mail;
    private String codigo;
    private String nuevaContrasena;

    public CambioContrasenaDTO() {}

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNuevaContrasena() { return nuevaContrasena; }
    public void setNuevaContrasena(String nuevaContrasena) { this.nuevaContrasena = nuevaContrasena; }
}
