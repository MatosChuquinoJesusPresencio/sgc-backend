package com.condominios.sgc.domain.exception;

public final class LogPrestamoCarritoException extends DominioException {
    private LogPrestamoCarritoException(String mensaje, TipoError type) {
        super(mensaje, type);
    }

    public static LogPrestamoCarritoException devolucionYaRegistrada() {
        return new LogPrestamoCarritoException("El carrito ya fue devuelto", TipoError.BAD_REQUEST);
    }

    public static LogPrestamoCarritoException logNoExistePorId(Long id) {
        return new LogPrestamoCarritoException("El log de prestamo con id " + id + " no existe", TipoError.NOT_FOUND);
    }

    public static LogPrestamoCarritoException carritoObligatorio() {
        return new LogPrestamoCarritoException("El carrito es obligatorio para el prestamo", TipoError.BAD_REQUEST);
    }

    public static LogPrestamoCarritoException solicitanteObligatorio() {
        return new LogPrestamoCarritoException("El tipo de solicitante es obligatorio", TipoError.BAD_REQUEST);
    }

    public static LogPrestamoCarritoException apartamentoIdObligatorio() {
        return new LogPrestamoCarritoException("El id del apartamento es obligatorio", TipoError.BAD_REQUEST);
    }

    public static LogPrestamoCarritoException yaTieneSolicitanteInquilino() {
        return new LogPrestamoCarritoException("El prestamo ya tiene un inquilino asignado. Remuevalo primero", TipoError.BAD_REQUEST);
    }

    public static LogPrestamoCarritoException yaTieneSolicitanteUsuario() {
        return new LogPrestamoCarritoException("El prestamo ya tiene un usuario asignado. Remuevalo primero", TipoError.BAD_REQUEST);
    }

    public static LogPrestamoCarritoException usuarioIdObligatorio() {
        return new LogPrestamoCarritoException("El id del usuario es obligatorio", TipoError.BAD_REQUEST);
    }

    public static LogPrestamoCarritoException inquilinoIdObligatorio() {
        return new LogPrestamoCarritoException("El id del inquilino es obligatorio", TipoError.BAD_REQUEST);
    }

    public static LogPrestamoCarritoException nombreSolicitanteObligatorio() {
        return new LogPrestamoCarritoException("El nombre del solicitante es obligatorio", TipoError.BAD_REQUEST);
    }

    public static LogPrestamoCarritoException dniSolicitanteObligatorio() {
        return new LogPrestamoCarritoException("El DNI del solicitante es obligatorio", TipoError.BAD_REQUEST);
    }
}
