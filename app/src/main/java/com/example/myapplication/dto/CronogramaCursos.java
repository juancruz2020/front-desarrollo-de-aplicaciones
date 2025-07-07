package com.example.myapplication.dto;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate; // ojo: Android puede no soportar bien java.time, mejor String para fechas

public class CronogramaCursos {

    @SerializedName("idCronograma")
    private Integer idCronograma;

    @SerializedName("sede")
    private Sede sede;

    @SerializedName("curso")
    private Curso curso;

    @SerializedName("fechaInicio")
    private String fechaInicio;  // Mejor String si no usás librería para fechas

    @SerializedName("fechaFin")
    private String fechaFin;

    @SerializedName("vacantesDisponibles")
    private Integer vacantesDisponibles;

    // getters y setters

    public Integer getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(Integer idCronograma) {
        this.idCronograma = idCronograma;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getVacantesDisponibles() {
        return vacantesDisponibles;
    }

    public void setVacantesDisponibles(Integer vacantesDisponibles) {
        this.vacantesDisponibles = vacantesDisponibles;
    }

    // Clases internas para Sede y Curso (simplificadas)

    public static class Sede {
        @SerializedName("nombreSede")
        private String nombreSede;

        // getters y setters
        public String getNombreSede() {
            return nombreSede;
        }
        public void setNombreSede(String nombreSede) {
            this.nombreSede = nombreSede;
        }
    }

    public static class Curso {
        @SerializedName("idCurso")
        private Integer idCurso;

        @SerializedName("descripcion")
        private String descripcion;

        // getters y setters
        public Integer getIdCurso() {
            return idCurso;
        }
        public void setIdCurso(Integer idCurso) {
            this.idCurso = idCurso;
        }
        public String getDescripcion() {
            return descripcion;
        }
        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
    }
}
