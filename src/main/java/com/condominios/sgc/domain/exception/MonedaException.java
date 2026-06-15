package com.condominios.sgc.domain.exception;

public class MonedaException extends DominioException {
    private MonedaException(String mensaje) {
        super(mensaje);
    }

    private MonedaException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static MonedaException noEncontrado() {
        return new MonedaException("moneda no encontrada", 404);
    }
}
