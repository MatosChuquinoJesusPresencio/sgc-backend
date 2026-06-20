package com.condominios.sgc.domain.shared.exception;

public class TokenException extends DominioException {
    private TokenException(String mensaje) {
        super(mensaje);
    }

    private TokenException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public static TokenException tipoRequerido() {
        return new TokenException("tipo de token no puede ser nulo");
    }

    public static TokenException tokenRequerido() {
        return new TokenException("token no puede estar vacío");
    }

    public static TokenException expiracionRequerida() {
        return new TokenException("expiración no puede ser nula");
    }

    public static TokenException usuarioRequerido() {
        return new TokenException("idUsuario no puede ser nulo");
    }

    public static TokenException noEncontrado() {
        return new TokenException("token no encontrado");
    }

    public static TokenException tokenExpirado() {
        return new TokenException("el token ha expirado");
    }

}
