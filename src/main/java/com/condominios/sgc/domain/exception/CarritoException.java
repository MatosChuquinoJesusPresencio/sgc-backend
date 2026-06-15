package com.condominios.sgc.domain.exception;

public class CarritoException extends DominioException {
    private CarritoException(String mensaje) {
        super(mensaje);
    }

    private CarritoException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
    }

    public static CarritoException codigoRequerido() {
        return new CarritoException("código no puede estar vacío");
    }

    public static CarritoException condominioRequerido() {
        return new CarritoException("idCondominio no puede ser nulo");
    }

    public static CarritoException estadoRequerido() {
        return new CarritoException("estado no puede ser nulo");
    }

    public static CarritoException estadoInvalido() {
        return new CarritoException("cambio de estado no permitido");
    }

    public static CarritoException noEncontrado() {
        return new CarritoException("carrito no encontrado", 404);
    }
}
