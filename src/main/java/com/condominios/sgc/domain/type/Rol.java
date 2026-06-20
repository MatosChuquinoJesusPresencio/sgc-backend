package com.condominios.sgc.domain.type;

import com.condominios.sgc.domain.shared.exception.AutenticacionException;

public enum Rol {
    SUPER_ADMINISTRADOR,
    ADMINISTRADOR_CONDOMINIO,
    PROPIETARIO,
    AGENTE_SEGURIDAD;

    public boolean puedeAsignarRol(Rol destino) {
        return switch (this) {
            case SUPER_ADMINISTRADOR -> true;
            case ADMINISTRADOR_CONDOMINIO -> destino == PROPIETARIO || destino == AGENTE_SEGURIDAD;
            default -> false;
        };
    }

    public void validarPuedeAsignarRol(Rol destino) {
        if (!puedeAsignarRol(destino))
            throw AutenticacionException.accesoDenegado();
    }
}
