package com.example.myapplication.dto;

public class IngredienteDTO {
    private String nombre;
    private int cantidad;
    private String unidad;
    private String observaciones;

    public IngredienteDTO(String nombre, int cantidad, String unidad, String observaciones) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.observaciones = observaciones;
    }

    public IngredienteDTO() {}

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}