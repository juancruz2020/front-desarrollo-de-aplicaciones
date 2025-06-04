package com.example.myapplication.dto;

public class CodigoDTO {
    private String mail;
    private String codigo;

    public CodigoDTO(String mail, String codigo) {
        this.codigo = codigo;
        this.mail = mail;
    }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
