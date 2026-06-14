package com.condominios.sgc.domain.exception;

public class ApartamentoException extends DominioException {
    private ApartamentoException(String mensaje) {
        super(mensaje);
    }

    public static ApartamentoException numeroRequerido() {
        return new ApartamentoException("numero debe ser positivo");
    }

    public static ApartamentoException metrajeRequerido() {
        return new ApartamentoException("metraje no puede ser nulo");
    }

    public static ApartamentoException pisoRequerido() {
        return new ApartamentoException("idPiso no puede ser nulo");
    }

    public static ApartamentoException propietarioRequerido() {
        return new ApartamentoException("idPropietario no puede ser nulo");
    }
}
