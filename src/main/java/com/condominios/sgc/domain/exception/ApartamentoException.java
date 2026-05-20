package com.condominios.sgc.domain.exception;

public final class ApartamentoException extends DominioException {
    private ApartamentoException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static ApartamentoException numeroInvalido() {
        return new ApartamentoException("El numero de apartamento debe ser mayor a 0", TipoError.BAD_REQUEST);
    }

    public static ApartamentoException metrajeInvalido() {
        return new ApartamentoException("El metraje debe ser mayor a 0", TipoError.BAD_REQUEST);
    }

    public static ApartamentoException derechoEstacionamientoInvalido() {
        return new ApartamentoException("El derecho de estacionamiento debe ser verdadero o falso", TipoError.BAD_REQUEST);
    }

    public static ApartamentoException pisoIdObligatorio() {
        return new ApartamentoException("El id del piso es obligatorio", TipoError.BAD_REQUEST);
    }

    public static ApartamentoException propietarioIdObligatorio() {
        return new ApartamentoException("El id del propietario es obligatorio", TipoError.BAD_REQUEST);
    }

    public static ApartamentoException noEncontrado(Long id) {
        return new ApartamentoException("Apartamento no encontrado con ID: " + id, TipoError.NOT_FOUND);
    }
}
