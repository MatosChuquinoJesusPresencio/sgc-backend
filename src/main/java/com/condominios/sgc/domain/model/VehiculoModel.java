package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.exception.VehiculoException;

public class VehiculoModel {
    private Long id;
    private String marca;
    private String color;
    private String modelo;
    private String placa;
    private TipoVehiculo tipo;
    private String propietarioId;
    private Long inquilinoId;
    private Long estacionamientoId;

    public VehiculoModel(
        Long id,
        String marca,
        String color,
        String modelo,
        String placa,
        TipoVehiculo tipo,
        String propietarioId,
        Long inquilinoId,
        Long estacionamientoId
    ) {
        this(marca, color, modelo, placa, tipo, propietarioId, inquilinoId, estacionamientoId);
        this.id = id;
    }

    public VehiculoModel(
        String marca,
        String color,
        String modelo,
        String placa,
        TipoVehiculo tipo,
        String propietarioId,
        Long inquilinoId,
        Long estacionamientoId
    ) {
        this.marca = requerirNoVacio(marca, VehiculoException::datosObligatorios);
        this.color = requerirNoVacio(color, VehiculoException::datosObligatorios);
        this.modelo = requerirNoVacio(modelo, VehiculoException::datosObligatorios);
        this.placa = requerirNoVacio(placa, VehiculoException::placaObligatoria);
        this.tipo = requerirNoNulo(tipo, VehiculoException::tipoVehiculoObligatorio);
        this.estacionamientoId = requerirNoNulo(estacionamientoId, VehiculoException::estacionamientoObligatorio);
        asignarDueno(propietarioId, inquilinoId);
    }

    public Long getId() { return id; }
    public String getMarca() { return marca; }
    public String getColor() { return color; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public TipoVehiculo getTipo() { return tipo; }
    public String getPropietarioId() { return propietarioId; }
    public Long getInquilinoId() { return inquilinoId; }
    public Long getEstacionamientoId() { return estacionamientoId; }

    public void asignarDueno(String propietarioId, Long inquilinoId) {
        boolean tienePropietario = propietarioId != null;
        boolean tieneInquilino = inquilinoId != null;

        if (tienePropietario == tieneInquilino) {
            throw VehiculoException.duenoInvalido();
        }

        this.propietarioId = propietarioId;
        this.inquilinoId = inquilinoId;
    }

    public void asignarEstacionamiento(Long estacionamientoId) {
        this.estacionamientoId = requerirNoNulo(estacionamientoId, VehiculoException::estacionamientoObligatorio);
    }

    public void actualizarDatos(String color) {
        this.color = requerirNoVacio(color, VehiculoException::datosObligatorios);
    }
}
