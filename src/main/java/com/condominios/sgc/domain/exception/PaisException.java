package com.condominios.sgc.domain.exception;

public class PaisException extends DominioException {
    private PaisException(String mensaje) {
        super(mensaje);
    }

    private PaisException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static PaisException noEncontrado() {
        return new PaisException("pais no encontrado", 404);
    }
}
