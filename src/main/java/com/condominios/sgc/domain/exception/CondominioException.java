package com.condominios.sgc.domain.exception;

public class CondominioException extends DominioException {
    private CondominioException(String mensaje) {
        super(mensaje);
    }

    private CondominioException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static CondominioException nombreRequerido() {
        return new CondominioException("nombre no puede estar vacío");
    }

    public static CondominioException paisRequerido() {
        return new CondominioException("idPais no puede ser nulo");
    }

    public static CondominioException ciudadRequerido() {
        return new CondominioException("idCiudad no puede ser nulo");
    }

    public static CondominioException noEncontrado() {
        return new CondominioException("condominio no encontrado", 404);
    }
}
