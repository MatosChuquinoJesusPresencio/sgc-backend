package com.condominios.sgc.application.dto;

public class CrearInquilinoRequest {
    private String nombres;
    private String apellidos;
    private String dni;
    private Long apartamentoId;

    public CrearInquilinoRequest() {
    }

    public CrearInquilinoRequest(String nombres, String apellidos, String dni, Long apartamentoId) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.apartamentoId = apartamentoId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Long getApartamentoId() {
        return apartamentoId;
    }

    public void setApartamentoId(Long apartamentoId) {
        this.apartamentoId = apartamentoId;
    }
}
