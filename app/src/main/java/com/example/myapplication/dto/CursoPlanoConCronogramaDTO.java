package com.example.myapplication.dto;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CursoPlanoConCronogramaDTO {
    private Integer idCurso;
    private String descripcion;
    private String contenidos;
    private String requerimientos;
    private Integer duracion;
    private BigDecimal precio;
    private String modalidad;

    private String nombreSede;
    private String fechaInicio;   // CAMBIO de LocalDate a String
    private String fechaFin;
    private Integer vacantesDisponibles;

    // Constructor
    public CursoPlanoConCronogramaDTO(
            Integer idCurso,
            String descripcion,
            String contenidos,
            String requerimientos,
            Integer duracion,
            BigDecimal precio,
            String modalidad,
            String nombreSede,
            String fechaInicio,
            String fechaFin,
            Integer vacantesDisponibles
    ) {
        this.idCurso = idCurso;
        this.descripcion = descripcion;
        this.contenidos = contenidos;
        this.requerimientos = requerimientos;
        this.duracion = duracion;
        this.precio = precio;
        this.modalidad = modalidad;
        this.nombreSede = nombreSede;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.vacantesDisponibles = vacantesDisponibles;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getContenidos() {
        return contenidos;
    }

    public String getRequerimientos() {
        return requerimientos;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getModalidad() {
        return modalidad;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public Integer getVacantesDisponibles() {
        return vacantesDisponibles;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setContenidos(String contenidos) {
        this.contenidos = contenidos;
    }

    public void setRequerimientos(String requerimientos) {
        this.requerimientos = requerimientos;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setVacantesDisponibles(Integer vacantesDisponibles) {
        this.vacantesDisponibles = vacantesDisponibles;
    }
}
