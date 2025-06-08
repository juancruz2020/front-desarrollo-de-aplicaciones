package com.example.myapplication;

import java.io.Serializable;
import java.util.List;

public class Receta implements Serializable {
    public String nombrePlato;
    public String descripcion;
    public int cantidadPorciones;
    public String tiempoValor;
    public String tiempoUnidad;
    public List<Ingrediente> ingredientes;
    public List<Paso> pasos;
    public String portadaPath;
}

