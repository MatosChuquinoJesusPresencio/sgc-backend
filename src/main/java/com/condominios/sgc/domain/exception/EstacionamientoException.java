package com.condominios.sgc.domain.exception;

public final class EstacionamientoException extends RuntimeException {
    private EstacionamientoException(String mensaje) {
        super(mensaje);
    }

    public static EstacionamientoException cantidadVehiculosInvalida() {
        return new EstacionamientoException("La cantidad maxima de vehiculos debe ser mayor a 0");
    }

    public static EstacionamientoException estacionamientoOcupado() {
        return new EstacionamientoException("El estacionamiento ya esta ocupado");
    }

    public static EstacionamientoException estacionamientoNoExistePorId(Long id) {
        return new EstacionamientoException("El estacionamiento con id " + id + " no existe");
    }

    public static EstacionamientoException estacionamientoYaExistePorId(Long id) {
        return new EstacionamientoException("El estacionamiento con id " + id + " ya existe");
    }

    public static EstacionamientoException estacionamientoNoExistePorNumero(Integer numero) {
        return new EstacionamientoException("El estacionamiento con numero " + numero + " no existe");
    }

    public static EstacionamientoException estacionamientoYaExistePorNumero(Integer numero) {
        return new EstacionamientoException("El estacionamiento con numero " + numero + " ya existe");
    }

    public static EstacionamientoException numeroObligatorio() {
        return new EstacionamientoException("El numero de estacionamiento es obligatorio");
    }

    public static EstacionamientoException tipoVehiculoInvalido() {
        return new EstacionamientoException("El tipo de vehiculo es obligatorio");
    }

    public static EstacionamientoException condominioIdObligatorio() {
        return new EstacionamientoException("El id del condominio es obligatorio");
    }

    public static EstacionamientoException apartamentoIdObligatorio() {
        return new EstacionamientoException("El id del apartamento es obligatorio");
    }
}
