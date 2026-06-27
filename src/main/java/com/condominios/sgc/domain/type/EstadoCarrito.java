package com.condominios.sgc.domain.type;

import com.condominios.sgc.domain.shared.exception.CarritoException;

public enum EstadoCarrito {
    EN_USO,
    MANTENIMIENTO,
    DISPONIBLE;

    public boolean puedeCambiarA(EstadoCarrito destino) {
        return switch (this) {
            case DISPONIBLE -> destino == EN_USO || destino == MANTENIMIENTO;
            case EN_USO -> destino == DISPONIBLE;
            case MANTENIMIENTO -> destino == DISPONIBLE;
        };
    }

    public void validarTransicion(EstadoCarrito destino) {
        if (!puedeCambiarA(destino))
            throw CarritoException.estadoInvalido();
    }
}
