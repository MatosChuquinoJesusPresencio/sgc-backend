package com.condominios.sgc.domain.shared.exception;

public class AutenticacionException extends DominioException {
    private AutenticacionException(String mensaje) {
        super(mensaje);
    }

    public static AutenticacionException credencialesInvalidas() {
        return new AutenticacionException("correo o contraseña incorrectos");
    }

    public static AutenticacionException tokenExpirado() {
        return new AutenticacionException("el token ha expirado");
    }

    public static AutenticacionException tokenInvalido() {
        return new AutenticacionException("el token es inválido o ha sido alterado");
    }

    public static AutenticacionException cuentaBloqueada() {
        return new AutenticacionException("la cuenta ha sido bloqueada temporalmente");
    }

    public static AutenticacionException cuentaDesactivada() {
        return new AutenticacionException("la cuenta está desactivada");
    }

    public static AutenticacionException accesoDenegado() {
        return new AutenticacionException("no tienes permiso para realizar esta acción");
    }

    public static AutenticacionException tipoTokenNoSoportado(String tipo) {
        return new AutenticacionException("tipo de token no soportado: " + tipo);
    }

    public static AutenticacionException errorGeneracionToken() {
        return new AutenticacionException("error generando token");
    }

    public static AutenticacionException usuarioNoAutenticado() {
        return new AutenticacionException("no hay un usuario autenticado en la sesion actual");
    }
}
