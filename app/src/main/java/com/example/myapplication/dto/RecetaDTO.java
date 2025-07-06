package com.example.myapplication.dto;

import java.util.List;

public class RecetaDTO {
    private Long idReceta;
    private String nickname;
    private String nombre;
    private String categoria;
    private String descripcion;
    private List<IngredienteDTO> ingredientes;
    private List<PasoDTO> pasos;
    private Integer porciones;
    private String urlImagen;


    public RecetaDTO(Long idReceta, String nickname, String nombre, String categoria, String descripcion, Integer porciones, List<IngredienteDTO> ingredientes, List<PasoDTO> pasos) {
        this.idReceta = idReceta;
        this.nickname = nickname;
        this.nombre = nombre;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.descripcion = descripcion;
        this.porciones = porciones;
    }

    public RecetaDTO(String nickname, String nombre, String categoria, String descripcion, Integer porciones, List<IngredienteDTO> ingredientes, List<PasoDTO> pasos) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.descripcion = descripcion;
        this.porciones = porciones;
    }
    public RecetaDTO() {}

    // Getters y setters


    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public List<IngredienteDTO> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<IngredienteDTO> ingredientes) { this.ingredientes = ingredientes; }

    public List<PasoDTO> getPasos() { return pasos; }
    public void setPasos(List<PasoDTO> pasos) { this.pasos = pasos; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getPorciones() {
        return porciones;
    }
    public void setPorciones(Integer porciones) {
        this.porciones = porciones;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    @Override
    public String toString() {
        return "RecetaDTO{" +
                "idReceta=" + idReceta +
                ", nickname='" + nickname + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ingredientes=" + ingredientes +
                ", pasos=" + pasos +
                ", porciones=" + porciones +
                ", urlImagen='" + urlImagen + '\'' +
                '}';
    }
}