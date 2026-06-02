package com.condominios.sgc.domain.exception;

public final class EstacionamientoException extends DominioException {
    private EstacionamientoException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static EstacionamientoException capacidadMaximaInvalida() {
        return new EstacionamientoException("La capacidad máxima de vehículos debe ser mayor a 0", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException estacionamientoOcupado() {
        return new EstacionamientoException("El estacionamiento ya está ocupado", TipoError.BAD_REQUEST);
    }
    public static EstacionamientoException noEncontrado(Long id) {
        return new EstacionamientoException("Estacionamiento no encontrado con ID: " + id, TipoError.NOT_FOUND);
    }

    public static EstacionamientoException numeroObligatorio() {
        return new EstacionamientoException("El número de estacionamiento es obligatorio", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException tipoVehiculoInvalido() {
        return new EstacionamientoException("El tipo de vehículo no es válido para este estacionamiento", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException condominioIdObligatorio() {
        return new EstacionamientoException("El id del condominio es obligatorio", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException apartamentoIdObligatorio() {
        return new EstacionamientoException("El id del apartamento es obligatorio", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException cantidadActualInvalida() {
        return new EstacionamientoException("La cantidad actual de vehículos debe ser mayor o igual a 0", TipoError.BAD_REQUEST);
    }

    public static EstacionamientoException estadoDisponibleObligatorio() {
        return new EstacionamientoException("El estado disponible es obligatorio", TipoError.BAD_REQUEST);
    }
}
