package com.condominios.sgc.domain.exception;

public final class EstacionamientoException extends DominioException {
    private EstacionamientoException(String mensaje, TipoError type) {
        super(mensaje, type);
    }

    public static EstacionamientoException capacidadMaximaInvalida() {
        return new EstacionamientoException("La capacidad maxima de vehiculos debe ser mayor a 0", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException estacionamientoOcupado() {
        return new EstacionamientoException("El estacionamiento ya esta ocupado", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException estacionamientoNoExistePorId(Long id) {
        return new EstacionamientoException("El estacionamiento con id " + id + " no existe", TipoError.NOT_FOUND);
    }

    public static EstacionamientoException estacionamientoYaExistePorId(Long id) {
        return new EstacionamientoException("El estacionamiento con id " + id + " ya existe", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException estacionamientoNoExistePorNumero(Integer numero) {
        return new EstacionamientoException("El estacionamiento con numero " + numero + " no existe", TipoError.NOT_FOUND);
    }

    public static EstacionamientoException estacionamientoYaExistePorNumero(Integer numero) {
        return new EstacionamientoException("El estacionamiento con numero " + numero + " ya existe", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException numeroObligatorio() {
        return new EstacionamientoException("El numero de estacionamiento es obligatorio", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException tipoVehiculoInvalido() {
        return new EstacionamientoException("El tipo de vehiculo es obligatorio", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException condominioIdObligatorio() {
        return new EstacionamientoException("El id del condominio es obligatorio", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException apartamentoIdObligatorio() {
        return new EstacionamientoException("El id del apartamento es obligatorio", TipoError.BAD_REQUEST);
    }
}
