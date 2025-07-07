package com.example.myapplication.dto;

import java.util.List;

public class RecetaDTO {
    private String nickname;
    private String nombre;
    private String categoria;
    private List<IngredienteDTO> ingredientes;
    private List<PasoDTO> pasos;
    private String descripcion;

    public RecetaDTO(String nickname, String nombre, String categoria, List<IngredienteDTO> ingredientes, List<PasoDTO> pasos, String descripcion) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.categoria = categoria;
        this.ingredientes = ingredientes;
        this.pasos = pasos;
        this.descripcion = descripcion;
    }

    public RecetaDTO() {}

    // Getters y setters
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
}