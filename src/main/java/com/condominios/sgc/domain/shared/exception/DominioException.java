package com.condominios.sgc.domain.shared.exception;

public abstract class DominioException extends RuntimeException {
    protected DominioException(String mensaje) {
        super(mensaje);
    }

    protected DominioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
