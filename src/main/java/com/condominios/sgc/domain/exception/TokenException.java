package com.condominios.sgc.domain.exception;

import com.condominios.sgc.domain.auxiliar.TipoToken;

public class TokenException extends DominioException {
    private TokenException(String mensaje) {
        super(mensaje);
    }

    private TokenException(String mensaje, int httpStatus) {
        super(mensaje, httpStatus);
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
        return new TokenException("expiracion no puede ser nula");
    }

    public static TokenException usuarioRequerido() {
        return new TokenException("idUsuario no puede ser nulo");
    }

    public static TokenException tokenYaUsado() {
        return new TokenException("el token ya fue usado");
    }

    public static TokenException noEncontrado() {
        return new TokenException("token no encontrado", 404);
    }

    public static TokenException tokenExpirado() {
        return new TokenException("el token ha expirado", 401);
    }

    public static TokenException tipoNoSoportado(TipoToken tipo) {
        return new TokenException("tipo de token no soportado: " + tipo);
    }

    public static TokenException errorGeneracion(String detalle, Throwable causa) {
        return new TokenException("error generando token: " + detalle, causa);
    }
}
