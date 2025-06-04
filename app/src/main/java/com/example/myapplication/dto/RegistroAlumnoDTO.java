package com.example.myapplication.dto;

import java.math.BigDecimal;

public class RegistroAlumnoDTO {
    private String mail;

    private String numeroTramite;

    private String nroTarjeta;

    private BigDecimal cuentaCorriente;


    public RegistroAlumnoDTO(String mail, String numeroTramite,String nroTarjeta,BigDecimal cuentaCorriente) {
        this.mail = mail;
        this.numeroTramite = numeroTramite;
        this.nroTarjeta = nroTarjeta;
        this.cuentaCorriente = cuentaCorriente;
    }

    public String getNumeroTramite() { return numeroTramite; }
    public void setNumeroTramite(String numeroTramite) { this.numeroTramite = numeroTramite; }

    public String getNroTarjeta() { return nroTarjeta; }
    public void setNroTarjeta(String nroTarjeta) { this.nroTarjeta = nroTarjeta; }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public BigDecimal getCuentaCorriente() {
        return cuentaCorriente;
    }
    public void setCuentaCorriente(BigDecimal cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }
}
