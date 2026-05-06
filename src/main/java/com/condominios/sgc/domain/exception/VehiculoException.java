package com.condominios.sgc.domain.exception;

public final class VehiculoException extends RuntimeException {
    private VehiculoException(String mensaje) {
        super(mensaje);
    }

    public static VehiculoException placaObligatoria() {
        return new VehiculoException("La placa del vehiculo es obligatoria");
    }

    public static VehiculoException vehiculoYaExistePorId(Long id) {
        return new VehiculoException("El vehiculo con id " + id + " ya existe");
    }

    public static VehiculoException vehiculoNoExistePorId(Long id) {
        return new VehiculoException("El vehiculo con id " + id + " no existe");
    }

    public static VehiculoException vehiculoYaExistePorPlaca(String placa) {
        return new VehiculoException("El vehiculo con placa " + placa + " ya existe");
    }

    public static VehiculoException vehiculoNoExistePorPlaca(String placa) {
        return new VehiculoException("El vehiculo con placa " + placa + " no existe");
    }

    public static VehiculoException datosObligatorios() {
        return new VehiculoException("Marca, color y modelo son obligatorios");
    }

    public static VehiculoException propietarioYaAsignado() {
        return new VehiculoException("El vehiculo ya tiene un propietario asignado. Remuevalo primero");
    }

    public static VehiculoException usuarioIdObligatorio() {
        return new VehiculoException("El id del usuario es obligatorio");
    }

    public static VehiculoException inquilinoIdObligatorio() {
        return new VehiculoException("El id del inquilino es obligatorio");
    }
}
