package com.condominios.sgc.domain.exception;

public class InquilinoException extends DominioException {
    private InquilinoException(String mensaje) {
        super(mensaje);
    }

    public static InquilinoException nombreRequerido() {
        return new InquilinoException("nombres no puede estar vacío");
    }

    public static InquilinoException apellidoRequerido() {
        return new InquilinoException("apellidos no puede estar vacío");
    }

    public static InquilinoException tipoDocumentoRequerido() {
        return new InquilinoException("tipoDocumento no puede ser nulo");
    }

    public static InquilinoException numeroDocumentoRequerido() {
        return new InquilinoException("numeroDocumento no puede estar vacío");
    }

    public static InquilinoException apartamentoRequerido() {
        return new InquilinoException("idApartamento no puede ser nulo");
    }

    public static InquilinoException noEncontrado() {
        return new InquilinoException("inquilino no encontrado");
    }

    public static InquilinoException limiteAlcanzado() {
        return new InquilinoException("límite de inquilinos alcanzado para este apartamento");
    }
}
