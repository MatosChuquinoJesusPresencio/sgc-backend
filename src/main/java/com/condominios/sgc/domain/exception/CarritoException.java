package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.DominioException;

public final class CarritoException extends DominioException {
    private CarritoException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static CarritoException codigoObligatorio() {
        return new CarritoException("El codigo del carrito es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CarritoException estadoObligatorio() {
        return new CarritoException("El estado inicial del carrito es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CarritoException condominioIdObligatorio() {
        return new CarritoException("El id del condominio es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CarritoException estadoNuevoObligatorio() {
        return new CarritoException("El estado nuevo es obligatorio", TipoError.BAD_REQUEST);
    }

    public static CarritoException transicionEstadoInvalida(String origen, String destino) {
        return new CarritoException("Transicion de estado invalida de " + origen + " a " + destino, TipoError.BAD_REQUEST);
    }
}
