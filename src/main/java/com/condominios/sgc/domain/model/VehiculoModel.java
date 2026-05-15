package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.exception.VehiculoException;

public class VehiculoModel {
    private Long id;
    private String marca;
    private String color;
    private String modelo;
    private String placa;
    private String propietarioId;
    private Long inquilinoId;

    public VehiculoModel(
        Long id,
        String marca,
        String color,
        String modelo,
        String placa,
        String propietarioId,
        Long inquilinoId
    ) {
        this(marca, color, modelo, placa, propietarioId, inquilinoId);
        this.id = id;
    }

    public VehiculoModel(
        String marca,
        String color,
        String modelo,
        String placa,
        String propietarioId,
        Long inquilinoId
    ) {
        this.marca = requerirNoVacio(marca, VehiculoException::datosObligatorios);
        this.color = requerirNoVacio(color, VehiculoException::datosObligatorios);
        this.modelo = requerirNoVacio(modelo, VehiculoException::datosObligatorios);
        this.placa = requerirNoVacio(placa, VehiculoException::placaObligatoria);
        asignarDueno(propietarioId, inquilinoId);
    }

    public Long getId() { return id; }
    public String getMarca() { return marca; }
    public String getColor() { return color; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public String getPropietarioId() { return propietarioId; }
    public Long getInquilinoId() { return inquilinoId; }

    public void asignarDueno(String propietarioId, Long inquilinoId) {
        boolean tienePropietario = propietarioId != null;
        boolean tieneInquilino = inquilinoId != null;

        if (tienePropietario == tieneInquilino) {
            throw VehiculoException.duenoInvalido();
        }

        this.propietarioId = propietarioId;
        this.inquilinoId = inquilinoId;
    }
}
