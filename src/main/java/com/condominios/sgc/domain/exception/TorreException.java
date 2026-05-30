package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

public final class TorreException extends DominioException {
    private TorreException(String mensaje, TipoError type) {
        super(mensaje, type);
    }

    public static TorreException nombreObligatorio() {
        return new TorreException("El nombre de la torre es obligatorio", TipoError.BAD_REQUEST);
    }

    public static TorreException condominioIdObligatorio() {
        return new TorreException("El id del condominio es obligatorio", TipoError.BAD_REQUEST);
    }

    public static TorreException noEncontrada(Long id) {
        return new TorreException("La torre con id " + id + " no existe", TipoError.NOT_FOUND);
    }
}
