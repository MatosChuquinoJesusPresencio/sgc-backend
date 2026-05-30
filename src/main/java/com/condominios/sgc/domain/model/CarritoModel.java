package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.exception.CarritoException;

public class CarritoModel {

    private Long id;
    private String codigo;
    private EstadoCarrito estado;
    private Long condominioId;

    public CarritoModel(
        Long id,
        String codigo,
        EstadoCarrito estadoInicial,
        Long condominioId
    ) {
        this.id = id;
        asignarDatos(codigo, estadoInicial, condominioId);
    }

    public CarritoModel(
        String codigo,
        EstadoCarrito estadoInicial,
        Long condominioId
    ) {
        this(null, codigo, estadoInicial, condominioId);
    }

    private void asignarDatos(String codigo, EstadoCarrito estado, Long condominioId) {
        this.codigo = requerirNoVacio(codigo, CarritoException::codigoObligatorio);
        this.estado = requerirNoNulo(estado, CarritoException::estadoObligatorio);
        this.condominioId = requerirNoNulo(condominioId, CarritoException::condominioIdObligatorio);
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public EstadoCarrito getEstado() { return estado; }
    public Long getCondominioId() { return condominioId; }

    public void cambiarEstado(EstadoCarrito nuevoEstado) {
        requerirNoNulo(nuevoEstado, CarritoException::estadoNuevoObligatorio);
        if (!esTransicionValida(this.estado, nuevoEstado)) {
            throw CarritoException.transicionEstadoInvalida(this.estado.name(), nuevoEstado.name());
        }
        this.estado = nuevoEstado;
    }

    private boolean esTransicionValida(EstadoCarrito actual, EstadoCarrito nuevo) {
        if (actual == nuevo) {
            return false;
        }
        if (actual == EstadoCarrito.DISPONIBLE) {
            return nuevo == EstadoCarrito.EN_USO || nuevo == EstadoCarrito.MANTENIMIENTO;
        }
        if (actual == EstadoCarrito.EN_USO) {
            return nuevo == EstadoCarrito.DISPONIBLE || nuevo == EstadoCarrito.MANTENIMIENTO;
        }
        if (actual == EstadoCarrito.MANTENIMIENTO) {
            return nuevo == EstadoCarrito.DISPONIBLE;
        }
        return false;
    }

    public void actualizar(String codigo, EstadoCarrito estado, Long condominioId) {
        asignarDatos(codigo, estado, condominioId);
    }
}