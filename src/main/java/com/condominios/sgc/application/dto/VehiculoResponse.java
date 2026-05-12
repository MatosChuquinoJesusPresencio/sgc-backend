package com.condominios.sgc.application.dto;

public class VehiculoResponse {
    private Long id;
    private String marca;
    private String color;
    private String modelo;
    private String placa;
    private String propietarioUsuarioId;
    private Long propietarioInquilinoId;

    public VehiculoResponse(Long id, String marca, String color, String modelo, String placa, String propietarioUsuarioId, Long propietarioInquilinoId) {
        this.id = id;
        this.marca = marca;
        this.color = color;
        this.modelo = modelo;
        this.placa = placa;
        this.propietarioUsuarioId = propietarioUsuarioId;
        this.propietarioInquilinoId = propietarioInquilinoId;
    }

    public Long getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getColor() {
        return color;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getPropietarioUsuarioId() {
        return propietarioUsuarioId;
    }

    public Long getPropietarioInquilinoId() {
        return propietarioInquilinoId;
    }
}
