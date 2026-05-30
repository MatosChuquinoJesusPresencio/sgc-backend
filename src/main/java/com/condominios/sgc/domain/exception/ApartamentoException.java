package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

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

    public static ApartamentoException noEncontrado() {
        return new ApartamentoException("El apartamento solicitado no se encuentra", TipoError.NOT_FOUND);
    }
}
