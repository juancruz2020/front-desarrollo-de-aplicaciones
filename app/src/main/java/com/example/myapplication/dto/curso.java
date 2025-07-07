package com.example.myapplication.dto;

import java.io.Serializable;

public class curso implements Serializable {
    private Integer idCurso;
    private String descripcion;
    private String contenidos;
    private String requerimientos;
    private Integer duracion;
    private String precio;  // en backend es BigDecimal, acá podés usar String o Double
    private String modalidad;

    // Getters y setters (o usar Lombok si usás Kotlin o plugins)
    public Integer getIdCurso() { return idCurso; }
    public void setIdCurso(Integer idCurso) { this.idCurso = idCurso; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getContenidos() { return contenidos; }
    public void setContenidos(String contenidos) { this.contenidos = contenidos; }

    public String getRequerimientos() { return requerimientos; }
    public void setRequerimientos(String requerimientos) { this.requerimientos = requerimientos; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public String getPrecio() { return precio; }
    public void setPrecio(String precio) { this.precio = precio; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
}
