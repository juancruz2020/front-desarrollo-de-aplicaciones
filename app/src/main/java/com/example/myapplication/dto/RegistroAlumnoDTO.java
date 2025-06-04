package com.example.myapplication.dto;

public class RegistroAlumnoDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String dni;

    public RegistroAlumnoDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
}
