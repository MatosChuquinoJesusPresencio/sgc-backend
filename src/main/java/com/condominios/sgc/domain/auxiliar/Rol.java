package com.condominios.sgc.domain.auxiliar;

public enum Rol {
    SUPER_ADMINISTRADOR,
    ADMINISTRADOR_CONDOMINIO,
    PROPIETARIO,
    AGENTE_SEGURIDAD;

    public boolean puedeAsignar(Rol objetivo) {
        return switch (this) {
            case SUPER_ADMINISTRADOR -> objetivo != SUPER_ADMINISTRADOR;
            case ADMINISTRADOR_CONDOMINIO -> objetivo == PROPIETARIO || objetivo == AGENTE_SEGURIDAD;
            case PROPIETARIO, AGENTE_SEGURIDAD -> false;
        };
    }
}
