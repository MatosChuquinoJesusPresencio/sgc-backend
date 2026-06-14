package com.condominios.sgc.domain.exception;

public class LogPrestamoCarritoException extends DominioException {
    private LogPrestamoCarritoException(String mensaje) {
        super(mensaje);
    }

    public static LogPrestamoCarritoException solicitanteRequerido() {
        return new LogPrestamoCarritoException("solicitante no puede ser nulo");
    }

    public static LogPrestamoCarritoException nombreSolicitanteRequerido() {
        return new LogPrestamoCarritoException("nombreSolicitante no puede estar vacío");
    }

    public static LogPrestamoCarritoException dniSolicitanteRequerido() {
        return new LogPrestamoCarritoException("dniSolicitante no puede estar vacío");
    }

    public static LogPrestamoCarritoException apartamentoRequerido() {
        return new LogPrestamoCarritoException("idApartamento no puede ser nulo");
    }

    public static LogPrestamoCarritoException carritoRequerido() {
        return new LogPrestamoCarritoException("idCarrito no puede ser nulo");
    }

    public static LogPrestamoCarritoException propietarioRequerido() {
        return new LogPrestamoCarritoException("idPropietario no puede ser nulo");
    }

    public static LogPrestamoCarritoException inquilinoRequerido() {
        return new LogPrestamoCarritoException("idInquilino no puede ser nulo");
    }

    public static LogPrestamoCarritoException penalizacionRequerida() {
        return new LogPrestamoCarritoException("penalizacion no puede ser nula");
    }

    public static LogPrestamoCarritoException devolucionYaRegistrada() {
        return new LogPrestamoCarritoException("la devolución ya fue registrada");
    }
}
