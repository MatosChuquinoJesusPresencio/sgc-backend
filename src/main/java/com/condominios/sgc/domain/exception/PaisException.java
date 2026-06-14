package com.condominios.sgc.domain.exception;

public class PaisException extends DominioException {
    private PaisException(String mensaje) {
        super(mensaje);
    }

    public static PaisException noEncontrado() {
        return new PaisException("pais no encontrado");
    }
}
