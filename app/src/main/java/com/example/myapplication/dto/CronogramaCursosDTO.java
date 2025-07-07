package com.example.myapplication.dto;


import java.time.LocalDate;

public class CronogramaCursosDTO {

    private Integer idCronograma;
    private SedeDTO sede;
    private curso curso;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer vacantesDisponibles;

    // Getters y Setters
    public Integer getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(Integer idCronograma) {
        this.idCronograma = idCronograma;
    }

    public SedeDTO getSede() {
        return sede;
    }

    public void setSede(SedeDTO sede) {
        this.sede = sede;
    }

    public curso getCurso() {
        return curso;
    }

    public void setCurso(curso curso) {
        this.curso = curso;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getVacantesDisponibles() {
        return vacantesDisponibles;
    }

    public void setVacantesDisponibles(Integer vacantesDisponibles) {
        this.vacantesDisponibles = vacantesDisponibles;
    }

    // Clase interna para sede
    public static class SedeDTO {
        private Integer idSede;
        private String nombreSede;
        private String direccionSede;
        private String telefonoSede;
        private String mailSede;
        private String whatsApp;
        private String tipoBonificacion;
        private Double bonificacionCursos;
        private String tipoPromocion;
        private Double promocionCursos;

        // Getters y Setters

        public Integer getIdSede() {
            return idSede;
        }

        public void setIdSede(Integer idSede) {
            this.idSede = idSede;
        }

        public String getNombreSede() {
            return nombreSede;
        }

        public void setNombreSede(String nombreSede) {
            this.nombreSede = nombreSede;
        }

        public String getDireccionSede() {
            return direccionSede;
        }

        public void setDireccionSede(String direccionSede) {
            this.direccionSede = direccionSede;
        }

        public String getTelefonoSede() {
            return telefonoSede;
        }

        public void setTelefonoSede(String telefonoSede) {
            this.telefonoSede = telefonoSede;
        }

        public String getMailSede() {
            return mailSede;
        }

        public void setMailSede(String mailSede) {
            this.mailSede = mailSede;
        }

        public String getWhatsApp() {
            return whatsApp;
        }

        public void setWhatsApp(String whatsApp) {
            this.whatsApp = whatsApp;
        }

        public String getTipoBonificacion() {
            return tipoBonificacion;
        }

        public void setTipoBonificacion(String tipoBonificacion) {
            this.tipoBonificacion = tipoBonificacion;
        }

        public Double getBonificacionCursos() {
            return bonificacionCursos;
        }

        public void setBonificacionCursos(Double bonificacionCursos) {
            this.bonificacionCursos = bonificacionCursos;
        }

        public String getTipoPromocion() {
            return tipoPromocion;
        }

        public void setTipoPromocion(String tipoPromocion) {
            this.tipoPromocion = tipoPromocion;
        }

        public Double getPromocionCursos() {
            return promocionCursos;
        }

        public void setPromocionCursos(Double promocionCursos) {
            this.promocionCursos = promocionCursos;
        }
    }

    // Clase interna para curso
    public static class CursoDTO {
        private Integer idCurso;
        private String descripcion;
        private String contenidos;
        private String requerimientos;
        private Integer duracion;
        private Double precio;
        private String modalidad;

        // Getters y Setters

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

        public String getContenidos() {
            return contenidos;
        }

        public void setContenidos(String contenidos) {
            this.contenidos = contenidos;
        }

        public String getRequerimientos() {
            return requerimientos;
        }

        public void setRequerimientos(String requerimientos) {
            this.requerimientos = requerimientos;
        }

        public Integer getDuracion() {
            return duracion;
        }

        public void setDuracion(Integer duracion) {
            this.duracion = duracion;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }

        public String getModalidad() {
            return modalidad;
        }

        public void setModalidad(String modalidad) {
            this.modalidad = modalidad;
        }
    }
}
