package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.exception.VehiculoException;

public class VehiculoModel {
    private Long id;
    private String marca;
    private String color;
    private String modelo;
    private String placa;
    private Long propietarioUsuarioId;
    private Long propietarioInquilinoId;

    public VehiculoModel(Long id, String marca, String color, String modelo, String placa) {
        validarPlaca(placa);
        validarDatos(marca, color, modelo);
        this.id = id;
        this.marca = marca;
        this.color = color;
        this.modelo = modelo;
        this.placa = placa;
    }

    private void validarPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw VehiculoException.placaObligatoria();
        }
    }

    private void validarDatos(String marca, String color, String modelo) {
        if (marca == null || marca.trim().isEmpty()
                || color == null || color.trim().isEmpty()
                || modelo == null || modelo.trim().isEmpty()) {
            throw VehiculoException.datosObligatorios();
        }
    }

    public Long getId() { return id; }
    public String getMarca() { return marca; }
    public String getColor() { return color; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public Long getPropietarioUsuarioId() { return propietarioUsuarioId; }
    public Long getPropietarioInquilinoId() { return propietarioInquilinoId; }

    public void asignarAUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw VehiculoException.usuarioIdObligatorio();
        }
        if (tienePropietario()) {
            throw VehiculoException.propietarioYaAsignado();
        }
        this.propietarioUsuarioId = usuarioId;
        this.propietarioInquilinoId = null;
    }

    public void asignarAInquilino(Long inquilinoId) {
        if (inquilinoId == null) {
            throw VehiculoException.inquilinoIdObligatorio();
        }
        if (tienePropietario()) {
            throw VehiculoException.propietarioYaAsignado();
        }
        this.propietarioInquilinoId = inquilinoId;
        this.propietarioUsuarioId = null;
    }

    public void removerPropietario() {
        this.propietarioUsuarioId = null;
        this.propietarioInquilinoId = null;
    }

    private boolean tienePropietario() {
        return this.propietarioUsuarioId != null || this.propietarioInquilinoId != null;
    }
}
