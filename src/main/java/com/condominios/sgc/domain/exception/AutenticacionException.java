package com.condominios.sgc.domain.exception;

public final class AutenticacionException extends DominioException {
    private AutenticacionException(String mensaje, TipoError tipo) {
        super(mensaje, tipo);
    }

    public static AutenticacionException credencialesInvalidas() {
        return new AutenticacionException("Credenciales inválidas", TipoError.BAD_REQUEST);
    }

    public static AutenticacionException usuarioNoRegistrado() {
        return new AutenticacionException("Usuario no registrado en el sistema", TipoError.NOT_FOUND);
    }

    public static AutenticacionException errorCreacion(String detalle) {
        return new AutenticacionException("Error al crear usuario: " + detalle, TipoError.BAD_REQUEST);
    }

    public static AutenticacionException tokenInvalido() {
        return new AutenticacionException("El token de autenticación no es válido o ha expirado", TipoError.FORBIDDEN);
    }

    public static AutenticacionException sesionExpirada() {
        return new AutenticacionException("La sesión ha expirado", TipoError.FORBIDDEN);
    }
}
