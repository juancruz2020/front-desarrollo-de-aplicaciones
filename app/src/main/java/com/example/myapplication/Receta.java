package com.example.myapplication;

import com.example.myapplication.dto.IngredienteDTO;
import com.example.myapplication.dto.PasoDTO;
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
    public List<IngredienteDTO> ingredientes;
    public List<PasoDTO> pasos;
    public String portadaPath;

    public Receta(){}

    public Receta(RecetaDTO dto) {
        this.idReceta = dto.getIdReceta();
        this.nombrePlato = dto.getNombre();
        this.descripcion = dto.getDescripcion();
        this.portadaPath = dto.getUrlImagen();
        this.ingredientes = dto.getIngredientes();
        this.pasos = dto.getPasos();
        this.cantidadPorciones = dto.getPorciones();
        this.tiempoValor = null;
        this.tiempoUnidad = null;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "idReceta=" + idReceta +
                ", nombrePlato='" + nombrePlato + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidadPorciones=" + cantidadPorciones +
                ", tiempoValor='" + tiempoValor + '\'' +
                ", tiempoUnidad='" + tiempoUnidad + '\'' +
                ", ingredientes=" + ingredientes +
                ", pasos=" + pasos +
                ", portadaPath='" + portadaPath + '\'' +
                '}';
    }
}

