package com.example.myapplication.dto;

import java.io.Serializable;

public class SedeDTO implements Serializable {
    public String nombre;
    public String descripcion;
    public String direccion;
    public String telefono;
    public String dias;
    public String horarios;
    public int imagenResId;
    

    public SedeDTO(String nombre, String descripcion, String direccion, String telefono, String dias, String horarios, int imagenResId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.telefono = telefono;
        this.dias = dias;
        this.horarios = horarios;
        this.imagenResId = imagenResId;
    }
}
