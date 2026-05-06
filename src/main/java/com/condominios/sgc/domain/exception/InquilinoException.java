package com.condominios.sgc.domain.exception;

public final class InquilinoException extends RuntimeException {
    private InquilinoException(String mensaje) {
        super(mensaje);
    }

    public static InquilinoException datosObligatorios() {
        return new InquilinoException("Nombres, apellidos y apartamento son obligatorios");
    }

    public static InquilinoException inquilinoYaExistePorId(Long id) {
        return new InquilinoException("El inquilino con id " + id + " ya existe");
    }

    public static InquilinoException inquilinoNoExistePorId(Long id) {
        return new InquilinoException("El inquilino con id " + id + " no existe");
    }

    public static InquilinoException inquilinoYaExistePorDni(String dni) {
        return new InquilinoException("El inquilino con DNI " + dni + " ya existe");
    }

    public static InquilinoException inquilinoNoExistePorDni(String dni) {
        return new InquilinoException("El inquilino con DNI " + dni + " no existe");
    }

    public static InquilinoException inquilinoYaTieneVehiculo() {
        return new InquilinoException("El inquilino ya tiene un vehiculo asignado");
    }

    public static InquilinoException apartamentoIdObligatorio() {
        return new InquilinoException("El id del apartamento es obligatorio");
    }

    public static InquilinoException vehiculoIdObligatorio() {
        return new InquilinoException("El id del vehiculo es obligatorio");
    }
}
