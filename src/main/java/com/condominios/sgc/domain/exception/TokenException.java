package com.condominios.sgc.domain.exception;

public class TokenException extends DominioException {
    private TokenException(String mensaje) {
        super(mensaje);
    }

    public static TokenException tipoRequerido() {
        return new TokenException("tipo de token no puede ser nulo");
    }

    public static TokenException tokenRequerido() {
        return new TokenException("token no puede estar vacío");
    }

    public static TokenException expiracionRequerida() {
        return new TokenException("expiracion no puede ser nula");
    }

    public static TokenException usuarioRequerido() {
        return new TokenException("idUsuario no puede ser nulo");
    }

    public static TokenException tokenYaUsado() {
        return new TokenException("el token ya fue usado");
    }
}
