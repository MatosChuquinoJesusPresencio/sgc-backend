package com.condominios.sgc.domain.auxiliar;

public enum Rol {
    SUPER_ADMINISTRADOR,
    ADMINISTRADOR_CONDOMINIO,
    PROPIETARIO,
    AGENTE_SEGURIDAD;

    public boolean puedeAsignarA(Rol otro) {
        return switch (this) {
            case SUPER_ADMINISTRADOR -> otro != SUPER_ADMINISTRADOR;
            case ADMINISTRADOR_CONDOMINIO -> otro == PROPIETARIO || otro == AGENTE_SEGURIDAD;
            case PROPIETARIO, AGENTE_SEGURIDAD -> false;
        };
    }
}
