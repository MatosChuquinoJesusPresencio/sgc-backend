package com.condominios.sgc.domain.exception;

public final class LogPrestamoCarritoException extends RuntimeException {
    private LogPrestamoCarritoException(String mensaje) {
        super(mensaje);
    }

    public static LogPrestamoCarritoException devolucionYaRegistrada() {
        return new LogPrestamoCarritoException("El carrito ya fue devuelto");
    }

    public static LogPrestamoCarritoException logNoExistePorId(Long id) {
        return new LogPrestamoCarritoException("El log de prestamo con id " + id + " no existe");
    }

    public static LogPrestamoCarritoException carritoObligatorio() {
        return new LogPrestamoCarritoException("El carrito es obligatorio para el prestamo");
    }

    public static LogPrestamoCarritoException solicitanteObligatorio() {
        return new LogPrestamoCarritoException("El tipo de solicitante es obligatorio");
    }

    public static LogPrestamoCarritoException apartamentoIdObligatorio() {
        return new LogPrestamoCarritoException("El id del apartamento es obligatorio");
    }

    public static LogPrestamoCarritoException yaTieneSolicitanteInquilino() {
        return new LogPrestamoCarritoException("El prestamo ya tiene un inquilino asignado. Remuevalo primero");
    }

    public static LogPrestamoCarritoException yaTieneSolicitanteUsuario() {
        return new LogPrestamoCarritoException("El prestamo ya tiene un usuario asignado. Remuevalo primero");
    }

    public static LogPrestamoCarritoException usuarioIdObligatorio() {
        return new LogPrestamoCarritoException("El id del usuario es obligatorio");
    }

    public static LogPrestamoCarritoException inquilinoIdObligatorio() {
        return new LogPrestamoCarritoException("El id del inquilino es obligatorio");
    }
}
