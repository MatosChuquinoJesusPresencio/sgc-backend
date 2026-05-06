package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.exception.CarritoException;

public class CarritoModel {
    private Long id;
    private String codigo;
    private EstadoCarrito estado;

    public CarritoModel(Long id, String codigo, EstadoCarrito estadoInicial) {
        this.id = id;
        validarYAsignarDatos(codigo, estadoInicial);
    }

    private void validarYAsignarDatos(String codigo, EstadoCarrito estadoInicial) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw CarritoException.codigoObligatorio();
        }
        this.codigo = codigo;
        if (estadoInicial == null) {
            throw CarritoException.estadoObligatorio();
        }
        this.estado = estadoInicial;
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public EstadoCarrito getEstado() { return estado; }

    public void cambiarEstado(EstadoCarrito nuevoEstado) {
        if (nuevoEstado == null) {
            throw CarritoException.estadoNuevoObligatorio();
        }
        if (!esTransicionValida(this.estado, nuevoEstado)) {
            throw CarritoException.transicionEstadoInvalida(this.estado.name(), nuevoEstado.name());
        }
        this.estado = nuevoEstado;
    }

    private boolean esTransicionValida(EstadoCarrito origen, EstadoCarrito destino) {
        if (origen == destino) {
            return false;
        }
        if (origen == EstadoCarrito.DISPONIBLE) {
            return destino == EstadoCarrito.EN_USO || destino == EstadoCarrito.MANTENIMIENTO;
        }
        if (origen == EstadoCarrito.EN_USO) {
            return destino == EstadoCarrito.DISPONIBLE || destino == EstadoCarrito.MANTENIMIENTO;
        }
        if (origen == EstadoCarrito.MANTENIMIENTO) {
            return destino == EstadoCarrito.DISPONIBLE;
        }
        return false;
    }
}
