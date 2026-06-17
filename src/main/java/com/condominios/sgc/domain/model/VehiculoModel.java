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
    private Long idCondominio;

    public VehiculoModel(Long id, String marca, String color, String modelo, String placa, 
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
            Long idCondominio) {
        this.id = null;
        this.marca = requerido(marca, VehiculoException::marcaRequerida);
        this.color = requerido(color, VehiculoException::colorRequerido);
        this.modelo = requerido(modelo, VehiculoException::modeloRequerido);
        this.placa = requerido(placa, VehiculoException::placaRequerida);
        this.tipo = noNulo(tipo, VehiculoException::tipoRequerido);
        this.idPropietario = null;
        this.idInquilino = null;
        this.idEstacionamiento = null;
        this.idCondominio = noNulo(idCondominio, VehiculoException::condominioRequerido);
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
    public Long getIdCondominio() { return idCondominio; }

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
