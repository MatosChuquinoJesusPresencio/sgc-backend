package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.exception.InquilinoException;

public class InquilinoModel {
    private Long id;
    private String nombres;
    private String apellidos;
    private String dni;
    private Long apartamentoId;
    private Long vehiculoId;

    public InquilinoModel(Long id, String nombres, String apellidos, String dni, Long apartamentoId) {
        validarDatos(nombres, apellidos, apartamentoId);
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.apartamentoId = apartamentoId;
    }

    private void validarDatos(String nombres, String apellidos, Long apartamentoId) {
        if (nombres == null || nombres.trim().isEmpty()
                || apellidos == null || apellidos.trim().isEmpty()) {
            throw InquilinoException.datosObligatorios();
        }
        if (apartamentoId == null) {
            throw InquilinoException.apartamentoIdObligatorio();
        }
    }

    public Long getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getDni() { return dni; }
    public Long getApartamentoId() { return apartamentoId; }
    public Long getVehiculoId() { return vehiculoId; }

    public void asignarVehiculo(Long vehiculoId) {
        if (vehiculoId == null) {
            throw InquilinoException.vehiculoIdObligatorio();
        }
        if (this.vehiculoId != null) {
            throw InquilinoException.inquilinoYaTieneVehiculo();
        }
        this.vehiculoId = vehiculoId;
    }

    public void removerVehiculo() {
        this.vehiculoId = null;
    }

    public void reasignarApartamento(Long nuevoApartamentoId) {
        if (nuevoApartamentoId == null) {
            throw InquilinoException.apartamentoIdObligatorio();
        }
        this.apartamentoId = nuevoApartamentoId;
    }
}
