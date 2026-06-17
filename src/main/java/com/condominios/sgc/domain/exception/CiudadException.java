package com.condominios.sgc.domain.exception;

public class CiudadException extends DominioException {
    private CiudadException(String mensaje) {
        super(mensaje);
    }

    private CiudadException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static CiudadException noEncontrado() {
        return new CiudadException("ciudad no encontrada", 404);
    }
}
