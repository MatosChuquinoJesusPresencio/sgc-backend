package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.shared.exception.VehiculoException;
import com.condominios.sgc.domain.shared.valueobject.PlacaVehiculo;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.type.TipoVehiculo;

public class VehiculoModel {
    private Long id;
    private String marca;
    private String color;
    private String modelo;
    private PlacaVehiculo placa;
    private TipoVehiculo tipo;
    private Long idPropietario;
    private Long idInquilino;
    private Long idEstacionamiento;
    private Long idCondominio;

    public VehiculoModel(Long id, String marca, String color, String modelo, PlacaVehiculo placa,
            TipoVehiculo tipo, Long idPropietario, Long idInquilino, Long idEstacionamiento,
            Long idCondominio) {
        this.id = id;
        this.marca = marca;
        this.color = color;
        this.modelo = modelo;
        this.placa = placa;
        this.tipo = tipo;
        this.idPropietario = idPropietario;
        this.idInquilino = idInquilino;
        this.idEstacionamiento = idEstacionamiento;
        this.idCondominio = idCondominio;
    }

    public VehiculoModel(String marca, String color, String modelo, String placa, TipoVehiculo tipo,
            Long idCondominio, Long idPropietario, Long idInquilino) {
        this.id = null;
        this.marca = requerido(marca, VehiculoException::marcaRequerida);
        this.color = requerido(color, VehiculoException::colorRequerido);
        this.modelo = requerido(modelo, VehiculoException::modeloRequerido);
        this.placa = new PlacaVehiculo(placa);
        this.tipo = noNulo(tipo, VehiculoException::tipoRequerido);
        asignarSolicitante(idPropietario, idInquilino);
        this.idEstacionamiento = null;
        this.idCondominio = noNulo(idCondominio, VehiculoException::condominioRequerido);
    }

    public Long getId() { return id; }
    public String getMarca() { return marca; }
    public String getColor() { return color; }
    public String getModelo() { return modelo; }
    public PlacaVehiculo getPlaca() { return placa; }
    public TipoVehiculo getTipo() { return tipo; }
    public Long getIdPropietario() { return idPropietario; }
    public Long getIdInquilino() { return idInquilino; }
    public Long getIdEstacionamiento() { return idEstacionamiento; }
    public Long getIdCondominio() { return idCondominio; }

    public void actualizar(String marca, String color, String modelo, String placa, TipoVehiculo tipo) {
        this.marca = requerido(marca, VehiculoException::marcaRequerida);
        this.color = requerido(color, VehiculoException::colorRequerido);
        this.modelo = requerido(modelo, VehiculoException::modeloRequerido);
        this.placa = new PlacaVehiculo(placa);
        this.tipo = noNulo(tipo, VehiculoException::tipoRequerido);
    }

    private void asignarPropietario(Long idPropietario) {
        this.idPropietario = noNulo(idPropietario, VehiculoException::propietarioRequerido);
        this.idInquilino = null;
    }

    private void asignarInquilino(Long idInquilino) {
        this.idInquilino = noNulo(idInquilino, VehiculoException::inquilinoRequerido);
        this.idPropietario = null;
    }

    private void asignarSolicitante(Long idPropietario, Long idInquilino) {
        boolean hayPropietario = idPropietario != null;
        boolean hayInquilino = idInquilino != null;
        if (hayInquilino == hayPropietario)
            throw VehiculoException.solicitanteInvalido();

        if (hayInquilino) {
            asignarInquilino(idInquilino);
        } else {
            asignarPropietario(idPropietario);
        }
    }

    public void asignarEstacionamiento(Long idEstacionamiento) {
        this.idEstacionamiento = noNulo(idEstacionamiento, VehiculoException::estacionamientoRequerido);
    }

    public void desasignarEstacionamiento() {
        this.idEstacionamiento = null;
    }

    public void reasignarSolicitante(Long idPropietario, Long idInquilino) {
        boolean hayPropietario = idPropietario != null;
        boolean hayInquilino = idInquilino != null;
        if (hayInquilino == hayPropietario)
            throw VehiculoException.solicitanteInvalido();

        if (hayInquilino) {
            asignarInquilino(idInquilino);
        } else {
            asignarPropietario(idPropietario);
        }
    }
}
