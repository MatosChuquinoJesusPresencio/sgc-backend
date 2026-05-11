package com.condominios.sgc.application.dto;

public class InquilinoResponse {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private Long apartamentoId;
    private Long vehiculoId;

    public InquilinoResponse() {
    }

    public InquilinoResponse(Long id, String nombres, String apellidos, String dni, Long apartamentoId, Long vehiculoId) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.apartamentoId = apartamentoId;
        this.vehiculoId = vehiculoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }
}
