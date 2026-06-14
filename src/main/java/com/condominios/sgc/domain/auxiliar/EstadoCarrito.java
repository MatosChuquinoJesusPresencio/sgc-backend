package com.condominios.sgc.domain.auxiliar;

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
}
