package com.condominios.sgc.domain.exception;

public class CiudadException extends DominioException {
    private CiudadException(String mensaje) {
        super(mensaje);
    }

    public static CiudadException noEncontrado() {
        return new CiudadException("ciudad no encontrada");
    }
}
