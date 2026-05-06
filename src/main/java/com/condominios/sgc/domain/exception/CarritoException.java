package com.condominios.sgc.domain.exception;

public final class CarritoException extends RuntimeException {
    private CarritoException(String mensaje) {
        super(mensaje);
    }

    public static CarritoException codigoObligatorio() {
        return new CarritoException("El codigo del carrito es obligatorio");
    }

    public static CarritoException estadoObligatorio() {
        return new CarritoException("El estado inicial del carrito es obligatorio");
    }

    public static CarritoException estadoNuevoObligatorio() {
        return new CarritoException("El estado nuevo es obligatorio");
    }

    public static CarritoException carritoNoExistePorId(Long id) {
        return new CarritoException("El carrito con id " + id + " no existe");
    }

    public static CarritoException carritoYaExistePorId(Long id) {
        return new CarritoException("El carrito con id " + id + " ya existe");
    }

    public static CarritoException carritoNoExistePorCodigo(String codigo) {
        return new CarritoException("El carrito con codigo " + codigo + " no existe");
    }

    public static CarritoException carritoYaExistePorCodigo(String codigo) {
        return new CarritoException("El carrito con codigo " + codigo + " ya existe");
    }

    public static CarritoException transicionEstadoInvalida(String origen, String destino) {
        return new CarritoException("Transicion de estado invalida de " + origen + " a " + destino);
    }
}
