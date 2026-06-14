package com.condominios.sgc.domain.exception;

public class ConfiguracionException extends DominioException {
    private ConfiguracionException(String mensaje) {
        super(mensaje);
    }

    public static ConfiguracionException maxAutosRequerido() {
        return new ConfiguracionException("maxAutos no puede ser nulo");
    }

    public static ConfiguracionException maxMotosRequerido() {
        return new ConfiguracionException("maxMotos no puede ser nulo");
    }

    public static ConfiguracionException penalizacionRequerida() {
        return new ConfiguracionException("penalizacionPorMin no puede ser nula");
    }

    public static ConfiguracionException maxTiempoRequerido() {
        return new ConfiguracionException("maxTiempoPrestamoMin no puede ser nulo");
    }

    public static ConfiguracionException maxEstacionamientosRequerido() {
        return new ConfiguracionException("maxEstacionamientosPorApartamento no puede ser nulo");
    }

    public static ConfiguracionException maxCarritosRequerido() {
        return new ConfiguracionException("maxCarritosPorApartamento no puede ser nulo");
    }

    public static ConfiguracionException maxVehiculosRequerido() {
        return new ConfiguracionException("maxVehiculosPorPropietario no puede ser nulo");
    }

    public static ConfiguracionException maxInquilinosRequerido() {
        return new ConfiguracionException("maxInquilinosPorApartamento no puede ser nulo");
    }

    public static ConfiguracionException condominioRequerido() {
        return new ConfiguracionException("idCondominio no puede ser nulo");
    }
}
