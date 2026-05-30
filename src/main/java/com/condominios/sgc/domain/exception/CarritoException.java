package com.condominios.sgc.domain.exception;

public final class CarritoException extends DominioException {
    private CarritoException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static CarritoException codigoObligatorio() {
        return new CarritoException("El código del carrito es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CarritoException estadoObligatorio() {
        return new CarritoException("El estado del carrito es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CarritoException condominioIdObligatorio() {
        return new CarritoException("El id del condominio es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CarritoException estadoNuevoObligatorio() {
        return new CarritoException("El nuevo estado del carrito es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CarritoException transicionEstadoInvalida(String origen, String destino) {
        return new CarritoException("Transición de estado inválida de " + origen + " a " + destino, TipoError.BAD_REQUEST);
    }

    public static CarritoException noEncontrado(Long id) {
        return new CarritoException("Carrito no encontrado con ID: " + id, TipoError.NOT_FOUND);
    }
}
