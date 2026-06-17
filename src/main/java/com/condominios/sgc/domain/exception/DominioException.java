package com.condominios.sgc.domain.exception;

public abstract class DominioException extends RuntimeException {
    private final int httpStatus;

    protected DominioException(String mensaje) {
        this(mensaje, 400);
    }

    protected DominioException(String mensaje, int httpStatus) {
        super(mensaje);
        this.httpStatus = httpStatus;
    }

    protected DominioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.httpStatus = 500;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
