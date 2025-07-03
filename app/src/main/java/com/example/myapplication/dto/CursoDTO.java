package com.example.myapplication.dto;

import java.io.Serializable;
import java.util.List;

public class CursoDTO implements Serializable {
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


    public CursoDTO(String nombre, String descripcion, String precio, String modalidad, String dia,
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
}
