package com.condominios.sgc.domain.exception;

public final class VehiculoException extends DominioException {
    private VehiculoException(String mensaje, TipoError type) {
        super(mensaje, type);
    }

    public static VehiculoException placaObligatoria() {
        return new VehiculoException("La placa del vehiculo es obligatoria", TipoError.BAD_REQUEST);
    }

    public static VehiculoException datosObligatorios() {
        return new VehiculoException("Marca, color y modelo son obligatorios", TipoError.BAD_REQUEST);
    }

    public static VehiculoException propietarioIdObligatorio() {
        return new VehiculoException("El id del propietario es obligatorio", TipoError.BAD_REQUEST);
    }

    public static VehiculoException inquilinoIdObligatorio() {
        return new VehiculoException("El id del inquilino es obligatorio", TipoError.BAD_REQUEST);
    }

    public static VehiculoException duenoInvalido() {
        return new VehiculoException("El vehiculo debe tener un propietario o un inquilino, pero no ambos ni ninguno", TipoError.BAD_REQUEST);
    }

    public static VehiculoException tipoVehiculoObligatorio() {
        return new VehiculoException("El tipo de vehiculo es obligatorio", TipoError.BAD_REQUEST);
    }

    public static VehiculoException estacionamientoObligatorio() {
        return new VehiculoException("El estacionamiento es obligatorio", TipoError.BAD_REQUEST);
    }
}
