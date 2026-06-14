package com.condominios.sgc.domain.exception;

public class TorreException extends DominioException {
    private TorreException(String mensaje) {
        super(mensaje);
    }

    public static TorreException nombreRequerido() {
        return new TorreException("nombre no puede estar vacío");
    }

    public static TorreException noEncontrado() {
        return new TorreException("torre no encontrada");
    }
}
