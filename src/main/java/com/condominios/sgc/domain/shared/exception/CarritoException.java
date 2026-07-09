package com.condominios.sgc.domain.shared.exception;

public class CarritoException extends DominioException {
    private CarritoException(String mensaje) {
        super(mensaje);
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
        return new CarritoException("carrito no encontrado");
    }

    public static CarritoException enUso() {
        return new CarritoException("no se puede eliminar un carrito que está en uso");
    }
}
