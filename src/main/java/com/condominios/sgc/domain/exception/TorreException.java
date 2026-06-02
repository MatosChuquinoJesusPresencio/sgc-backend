package com.condominios.sgc.domain.exception;

public final class TorreException extends DominioException {
    private TorreException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static TorreException nombreObligatorio() {
        return new TorreException("El nombre de la torre es obligatorio", TipoError.BAD_REQUEST);
    }

    public static TorreException condominioIdObligatorio() {
        return new TorreException("El id del condominio es obligatorio", TipoError.BAD_REQUEST);
    }

    public static TorreException noEncontrada(Long id) {
        return new TorreException("Torre no encontrada con ID: " + id, TipoError.NOT_FOUND);
    }
}
