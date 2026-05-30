package com.condominios.sgc.domain.auxiliar;

public abstract class DominioException extends RuntimeException {

    private final TipoError tipo;

    public enum TipoError {
        NOT_FOUND,
        BAD_REQUEST,
        FORBIDDEN
    }

    protected DominioException(String mensaje, TipoError tipo) {
        super(mensaje);
        this.tipo = tipo;
    }

    public TipoError getTipo() {
        return tipo;
    }
}
