package com.example.myapplication.dto;

public class IdCursosDTO {
    private int idCurso;

    public IdCursosDTO(int id) {
        this.idCurso = id;
    }
    public IdCursosDTO(){

    }

    public void setId(int id) {
        this.idCurso = id;
    }

    public int getId() {
        return idCurso;
    }
}
