package com.example.myapplication;

import java.io.Serializable;

public class Paso implements Serializable {
    int numeroPaso;
    String descripcion;
    String mediaPath; // puede ser null



    public Paso(int numeroPaso, String descripcion, String mediaPath) {
        this.numeroPaso = numeroPaso;
        this.descripcion = descripcion;
        this.mediaPath = mediaPath;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public int getNumeroPaso() {
        return numeroPaso;
    }

    public void setNumeroPaso(int numeroPaso) {
        this.numeroPaso = numeroPaso;
    }
}

