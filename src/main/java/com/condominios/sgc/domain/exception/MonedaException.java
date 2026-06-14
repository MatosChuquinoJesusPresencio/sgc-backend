package com.condominios.sgc.domain.exception;

public class MonedaException extends DominioException {
    private MonedaException(String mensaje) {
        super(mensaje);
    }

    public static MonedaException noEncontrado() {
        return new MonedaException("moneda no encontrada");
    }
}
