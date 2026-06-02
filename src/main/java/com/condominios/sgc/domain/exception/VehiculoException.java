package com.condominios.sgc.domain.exception;

public final class VehiculoException extends DominioException {
    private VehiculoException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static VehiculoException placaObligatoria() {
        return new VehiculoException("La placa del vehículo es obligatoria", TipoError.BAD_REQUEST);
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
        return new VehiculoException("El vehículo debe tener un propietario o un inquilino, pero no ambos ni ninguno",
                TipoError.BAD_REQUEST);
    }

    public static VehiculoException tipoVehiculoObligatorio() {
        return new VehiculoException("El tipo de vehículo es obligatorio", TipoError.BAD_REQUEST);
    }

    public static VehiculoException estacionamientoObligatorio() {
        return new VehiculoException("El estacionamiento es obligatorio", TipoError.BAD_REQUEST);
    }

    public static VehiculoException noEncontrado(Long id) {
        return new VehiculoException("Vehículo no encontrado con ID: " + id, TipoError.NOT_FOUND);
    }
}
