package com.example.myapplication.dto;

import java.io.Serializable;
import java.util.List;

public class cursoFront implements Serializable {
    public String nombre;
    public String descripcion;
    public String precio;
    public String modalidad;
    public String dia;
    public String horario;
    public String fechaInicio;
    public String fechaFin;
    public String objetivo;
    public String temas;
    public String insumos;
    public String montoFinal;
    public int imagenResId;
    public int iconoModalidadResId;

    public int fotoProfesorResId;
    public String nombreProfesor;
    public String descripcionProfesor;

    public List<String> sedes;


    public cursoFront(String nombre, String descripcion, String precio, String modalidad, String dia,
                    String horario, String fechaInicio, String fechaFin, String objetivo, String temas,
                    String insumos, String montoFinal, int imagenResId, int iconoModalidadResId,
                    int fotoProfesorResId, String nombreProfesor, String descripcionProfesor, List<String> sedes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.modalidad = modalidad;
        this.dia = dia;
        this.horario = horario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.objetivo = objetivo;
        this.temas = temas;
        this.insumos = insumos;
        this.montoFinal = montoFinal;
        this.imagenResId = imagenResId;
        this.iconoModalidadResId = iconoModalidadResId;
        this.fotoProfesorResId = fotoProfesorResId;
        this.nombreProfesor = nombreProfesor;
        this.descripcionProfesor = descripcionProfesor;
        this.sedes = sedes;
    }

    public cursoFront() {

    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public void setTemas(String temas) {
        this.temas = temas;
    }

    public void setInsumos(String insumos) {
        this.insumos = insumos;
    }

    public void setMontoFinal(String montoFinal) {
        this.montoFinal = montoFinal;
    }

    public void setImagenResId(int imagenResId) {
        this.imagenResId = imagenResId;
    }

    public void setIconoModalidadResId(int iconoModalidadResId) {
        this.iconoModalidadResId = iconoModalidadResId;
    }

    public void setFotoProfesorResId(int fotoProfesorResId) {
        this.fotoProfesorResId = fotoProfesorResId;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public void setDescripcionProfesor(String descripcionProfesor) {
        this.descripcionProfesor = descripcionProfesor;
    }

    public void setSedes(List<String> sedes) {
        this.sedes = sedes;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public String getModalidad() {
        return modalidad;
    }

    public String getDia() {
        return dia;
    }

    public String getHorario() {
        return horario;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getTemas() {
        return temas;
    }

    public String getInsumos() {
        return insumos;
    }

    public String getMontoFinal() {
        return montoFinal;
    }

    public int getImagenResId() {
        return imagenResId;
    }

    public int getIconoModalidadResId() {
        return iconoModalidadResId;
    }

    public int getFotoProfesorResId() {
        return fotoProfesorResId;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public String getDescripcionProfesor() {
        return descripcionProfesor;
    }

    public List<String> getSedes() {
        return sedes;
    }
}
