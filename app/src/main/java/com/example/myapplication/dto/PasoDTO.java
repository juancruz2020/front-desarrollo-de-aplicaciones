package com.example.myapplication.dto;

public class PasoDTO {
    private int nroPaso;
    private String texto;
    private String url;

    public PasoDTO(int nroPaso, String texto, String url) {
        this.nroPaso = nroPaso;
        this.texto = texto;
        this.url = url;
    }

    public PasoDTO() {}

    // Getters y setters
    public int getNroPaso() { return nroPaso; }
    public void setNroPaso(int nroPaso) { this.nroPaso = nroPaso; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}