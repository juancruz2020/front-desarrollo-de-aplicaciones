package com.example.myapplication;

import com.example.myapplication.dto.RecetaDTO;

import java.io.Serializable;
import java.util.List;

public class Receta implements Serializable {
    public Long idReceta;
    public String nombrePlato;
    public String descripcion;
    public int cantidadPorciones;
    public String tiempoValor;
    public String tiempoUnidad;
    public List<Ingrediente> ingredientes;
    public List<Paso> pasos;
    public String portadaPath;

    public Receta(){}

    public Receta(RecetaDTO dto) {
        this.idReceta = dto.getIdReceta();
        this.nombrePlato = dto.getNombre();
        this.descripcion = dto.getDescripcion();
        this.portadaPath = null;
        this.ingredientes = null;
        this.pasos = null;
        this.cantidadPorciones = 0;
        this.tiempoValor = null;
        this.tiempoUnidad = null;
    }
}

