package com.condominios.sgc.domain.exception;

public final class InquilinoException extends DominioException {
    private InquilinoException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static InquilinoException nombresObligatorios() {
        return new InquilinoException("Los nombres son obligatorios", TipoError.BAD_REQUEST);
    }

    public static InquilinoException apellidosObligatorios() {
        return new InquilinoException("Los apellidos son obligatorios", TipoError.BAD_REQUEST);
    }

    public static InquilinoException apartamentoIdObligatorio() {
        return new InquilinoException("El id del apartamento es obligatorio", TipoError.BAD_REQUEST);
    }

    public static InquilinoException dniObligatorio() {
        return new InquilinoException("El DNI es obligatorio", TipoError.BAD_REQUEST);
    }

    public static InquilinoException noEncontrado(Long id) {
        return new InquilinoException("Inquilino no encontrado con ID: " + id, TipoError.NOT_FOUND);
    }
}
