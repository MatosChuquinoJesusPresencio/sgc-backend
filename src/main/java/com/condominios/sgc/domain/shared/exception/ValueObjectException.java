package com.condominios.sgc.domain.shared.exception;

public class ValueObjectException extends DominioException {
    private ValueObjectException(String mensaje) {
        super(mensaje);
    }

    public static ValueObjectException correoRequerido() {
        return new ValueObjectException("correo no puede estar vacío");
    }

    public static ValueObjectException correoInvalido() {
        return new ValueObjectException("correo no tiene un formato válido");
    }

    public static ValueObjectException telefonoRequerido() {
        return new ValueObjectException("teléfono no puede estar vacío");
    }

    public static ValueObjectException telefonoInvalido() {
        return new ValueObjectException(
                "teléfono debe contener entre 7 y 15 dígitos y puede iniciar con '+'");
    }

    public static ValueObjectException placaRequerida() {
        return new ValueObjectException("placa no puede estar vacía");
    }

    public static ValueObjectException nombresRequeridos() {
        return new ValueObjectException("nombres no puede estar vacío");
    }

    public static ValueObjectException apellidosRequeridos() {
        return new ValueObjectException("apellidos no puede estar vacío");
    }

    public static ValueObjectException tipoDocumentoRequerido() {
        return new ValueObjectException("tipo de documento no puede ser nulo");
    }

    public static ValueObjectException documentoRequerido() {
        return new ValueObjectException("número de documento no puede estar vacío");
    }
}
