package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import com.condominios.sgc.domain.exception.VehiculoException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class VehiculoModel {
    private Long id;
    private String marca;
    private String color;
    private String modelo;
    private String placa;
    private TipoVehiculo tipo;
    private Long idPropietario;
    private Long idInquilino;
    private Long idEstacionamiento;

    public VehiculoModel(Long id, String marca, String color, String modelo, String placa, 
            TipoVehiculo tipo, Long idPropietario, Long idInquilino, Long idEstacionamiento) {
        this.id = id;
        this.marca = marca;
        this.color = color;
        this.modelo = modelo;
        this.placa = placa;
        this.tipo = tipo;
        this.idPropietario = idPropietario;
        this.idInquilino = idInquilino;
        this.idEstacionamiento = idEstacionamiento;
    }

    public VehiculoModel(String marca, String color, String modelo, String placa, TipoVehiculo tipo) {
        this.id = null;
        this.marca = requerido(marca, VehiculoException::marcaRequerida);
        this.color = requerido(color, VehiculoException::colorRequerido);
        this.modelo = requerido(modelo, VehiculoException::modeloRequerido);
        this.placa = requerido(placa, VehiculoException::placaRequerida);
        this.tipo = noNulo(tipo, VehiculoException::tipoRequerido);
        this.idPropietario = null;
        this.idInquilino = null;
        this.idEstacionamiento = null;
    }

    public Long getId() { return id; }
    public String getMarca() { return marca; }
    public String getColor() { return color; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public TipoVehiculo getTipo() { return tipo; }
    public Long getIdPropietario() { return idPropietario; }
    public Long getIdInquilino() { return idInquilino; }
    public Long getIdEstacionamiento() { return idEstacionamiento; }

    public void actualizar(String marca, String color, String modelo, String placa, TipoVehiculo tipo) {
        this.marca = requerido(marca, VehiculoException::marcaRequerida);
        this.color = requerido(color, VehiculoException::colorRequerido);
        this.modelo = requerido(modelo, VehiculoException::modeloRequerido);
        this.placa = requerido(placa, VehiculoException::placaRequerida);
        this.tipo = noNulo(tipo, VehiculoException::tipoRequerido);
    }

    public void asignarPropietario(Long idPropietario) {
        this.idPropietario = noNulo(idPropietario, VehiculoException::propietarioRequerido);
        this.idInquilino = null;
    }

    public void desasignarPropietario() {
        this.idPropietario = null;
    }

    public void asignarInquilino(Long idInquilino) {
        this.idInquilino = noNulo(idInquilino, VehiculoException::inquilinoRequerido);
        this.idPropietario = null;
    }

    public void desasignarInquilino() {
        this.idInquilino = null;
    }

    public void asignarEstacionamiento(Long idEstacionamiento) {
        this.idEstacionamiento = noNulo(idEstacionamiento, VehiculoException::estacionamientoRequerido);
    }

    public void desasignarEstacionamiento() {
        this.idEstacionamiento = null;
    }
}
